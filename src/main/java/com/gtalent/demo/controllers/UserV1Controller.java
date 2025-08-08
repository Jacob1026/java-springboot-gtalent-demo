package com.gtalent.demo.controllers;

import com.gtalent.demo.models.User;
import com.gtalent.demo.requests.CreateUserRequest;
import com.gtalent.demo.requests.UpdateUserRequest;
import com.gtalent.demo.responses.CreateUserResponse;
import com.gtalent.demo.responses.GetUserResponse;
import com.gtalent.demo.responses.UpdateUserResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/users")
@CrossOrigin("*")
public class UserV1Controller {
//    注入1-@Autowired
//    private  JdbcTemplate jdbcTemplate;

    //注入2-由建構子注入，彈性比較高可以增加邏輯
    private final JdbcTemplate jbdcTemplate;

    @Autowired
    public UserV1Controller(JdbcTemplate jbdcTemplate) {
        this.jbdcTemplate = jbdcTemplate;
    }

    @PostMapping
    public ResponseEntity<CreateUserResponse> createUser(@RequestBody CreateUserRequest request) {
        String sql = "insert into users (username,email) values(?,?) ";
        //因為update 回傳 int,update了幾行
        int rowsAffected = jbdcTemplate.update(sql, request.getUsername(), request.getEmail());
        if (rowsAffected > 0) {
            CreateUserResponse response = new CreateUserResponse(request.getUsername());
            return new ResponseEntity<>(response, HttpStatus.CREATED);//201
        }
        return ResponseEntity.badRequest().build();//400
    }

    @GetMapping
    public ResponseEntity<List<GetUserResponse>> getAllUsers() {
        String sql = "select * from users";
        //query 回傳List
        List<User> users = jbdcTemplate.query(sql, new BeanPropertyRowMapper<>(User.class));
        return ResponseEntity.ok(users.stream().map(GetUserResponse::new).toList());
    }

    @GetMapping("/{id}")
    public ResponseEntity<GetUserResponse> getUserById(@PathVariable int id) {
        //報錯500可能會洩漏資料用Try catch
        String sql = "select * from users where id=?";
        try {
            //queryForObject 找到正好一筆回傳單一物件
            User user = jbdcTemplate.queryForObject(sql, new BeanPropertyRowMapper<>(User.class));
            return ResponseEntity.ok(new GetUserResponse(user));

        } catch (EmptyResultDataAccessException e) {
            return ResponseEntity.notFound().build();
        }
    }
    @PutMapping("/{id}")
    public ResponseEntity<UpdateUserResponse> updateUserById (@PathVariable int id, @RequestBody UpdateUserRequest request){
        String sql = "update  users  set username=? where id=?";
        int rowsAffected = jbdcTemplate.update(sql,request.getUserName(),id);
        if(rowsAffected > 0){
            UpdateUserResponse response =new UpdateUserResponse(request.getUserName());
            return ResponseEntity.ok(response);
        }else {
            return ResponseEntity.notFound().build();
        }
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletUserById (@PathVariable int id){
        String sql = "delete from users where id = ?";
        int rowsAffected = jbdcTemplate.update(sql,id);
        if(rowsAffected > 0){
            return ResponseEntity.noContent().build();
        }else {
            return ResponseEntity.notFound().build();
        }
    }
    @GetMapping("/search")
    public  ResponseEntity<List<GetUserResponse>>searchUser(@RequestParam String keyword) {
        String sql ="SELECT * FROM users WHERE LOWER(username) LIKE ?";
        List<User> users = jbdcTemplate.query(sql, new BeanPropertyRowMapper<>(User.class), keyword.toLowerCase());
        List<GetUserResponse> response = users.stream().map(GetUserResponse::new).toList();
        return ResponseEntity.ok(response);
    }



}
