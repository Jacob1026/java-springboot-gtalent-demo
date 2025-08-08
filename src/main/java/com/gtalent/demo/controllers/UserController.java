package com.gtalent.demo.controllers;

import com.gtalent.demo.models.User;
import com.gtalent.demo.requests.CreateUserRequest;
import com.gtalent.demo.requests.UpdateUserRequest;
import com.gtalent.demo.responses.CreateUserResponse;
import com.gtalent.demo.responses.GetUserResponse;
import com.gtalent.demo.responses.UpdateUserResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

@RestController
//把共同的("/users")提取出來，下面就都是/users底下的東西
@RequestMapping("/users")
public class UserController {
    private  final Map<Integer,User> mockUser = new HashMap<>();
    //計數器，模擬DB自增ID效果
    private  final AtomicInteger atomicInteger =new AtomicInteger();

  //建構子:每當UserController被讀取時要做的事情
    public UserController(){
        //假資料庫
        mockUser.put(1,new User(1,"aaaa","aaaa@gmail.com"));
        mockUser.put(2,new User(2,"bbbb","bbbb@gmail.com"));
        mockUser.put(3,new User(3,"cccc","cccc@gmail.com"));
        atomicInteger.set(4);
    }

    @GetMapping
    public ResponseEntity<List<GetUserResponse>>getAllUsers(){
        List<User> userList =new ArrayList<>(mockUser.values());
        List<GetUserResponse> responses = new ArrayList<>();
        for(User user:userList){
            GetUserResponse response = new GetUserResponse(user.getUsername(),user.getId());
            responses.add(response);
        }
        return ResponseEntity.ok(responses);
    }

    @GetMapping("/{id}")
    //@PathVariable路徑上的參數id，要從{id}取得，兩者名稱必須依樣
    //ResponseEntity<T> 用來更改狀態碼
    public ResponseEntity<GetUserResponse> getUserById(@PathVariable int id){

        User user = mockUser.get(id);
        if(user == null){
            return ResponseEntity.notFound().build();
        }
            GetUserResponse response = new GetUserResponse(user.getUsername(),user.getId());
            return ResponseEntity.ok(response);
        }

//  更新使用者資訊
    @PutMapping("/{id}")
    public ResponseEntity<UpdateUserResponse> updataeById1(@PathVariable int id, @RequestBody UpdateUserRequest request){
        User user = mockUser.get(id);
        if(user == null){
            return ResponseEntity.notFound().build();
        }
//        updata user  request body from postman
//        因為同個記憶體位址所以直接set會改掉原本資料，重新讀取後會恢復原本資料

        user.setUsername(request.getUserName());
//        user.setEmail(request.getEmail());
        UpdateUserResponse  response = new UpdateUserResponse(user.getUsername());
        return ResponseEntity.ok(response);
//        更新DB資料庫，需要再覆蓋原本DB資料
//        User user = db.getUser();
//        user.setUsername(request.getUsername());
//        user.setEmail(request.getEmail());
//        db.save(user);
    }
    //新增使用者要新的記憶體位置所以要new
    @PostMapping
//    原本用User,把回覆跟請求都用一個Class去過濾資料，避免資料外流
    public ResponseEntity<CreateUserResponse> createUser(@RequestBody CreateUserRequest request){
//        方法1: new user id = 把map裡面最大的ID找出來+1
//        方法2:用計數器
        int newId = atomicInteger.getAndIncrement();
        User user = new User(newId ,request.getUsername(),request.getEmail());
        mockUser.put(newId,user);
        //序列化回傳Jason
        CreateUserResponse respone = new CreateUserResponse(user.getUsername());
        return new ResponseEntity<>(respone, HttpStatus.CREATED);
    }
//    刪除使用者ById
    @DeleteMapping("/{id}")
    public ResponseEntity<User> deletUserById(@PathVariable int id) {
        User user = mockUser.get(id);
        if (user == null) {
            return ResponseEntity.notFound().build();
        }
        mockUser.remove(user);
//       204:操作成功，無須提供進一步訊息
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/search")
    public  ResponseEntity<List<GetUserResponse>>searchUser(@RequestParam String keyword){
        List<GetUserResponse> results =mockUser.values()
                                            .stream()
                                            //宣告 user 變數，filter過濾符合條件User資料，結果為T的
                                            .filter(user -> user.getUsername().toLowerCase().contains(keyword.toLowerCase()))
                                            //加工mapping map(user -> new GetUserResponse(user)) 又因型態是User故GetUserRespons建構子裡要有符合Usesr型態的
                                            .map(GetUserResponse :: new)
                                            .toList();
//                                           .map(this::toGetUserResponse)
//        List<GetUserResponse> responses = new ArrayList<>();
//        for(GetUserResponse user : results){
//            GetUserResponse response = new GetUserResponse(user.getUsername(),user.getId());
//            responses.add(response);
//        }
        return ResponseEntity.ok(results);

    }

    @GetMapping("/normal")
    public ResponseEntity<List<GetUserResponse>>getNormallUser(){
        List<GetUserResponse> getNormallUser =mockUser.values()
                .stream()
//                .filter(Predicate.not(user -> user.getUsername() 反轉filter為T的結果
                .filter(user -> !user.getUsername().toLowerCase().contains("admin"))
                .map(GetUserResponse :: new)
                .toList();
        return ResponseEntity.ok(getNormallUser);
    }

    private GetUserResponse toGetUserResponse (User user){
        return  new GetUserResponse(user.getEmail(), user.getId());
    }
}


