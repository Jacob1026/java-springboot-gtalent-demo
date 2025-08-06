package com.gtalent.demo;

import com.gtalent.demo.models.User;
import com.gtalent.demo.repositories.UserRepository;
import com.gtalent.demo.requests.CreateUserRequest;
import com.gtalent.demo.requests.UpdateUserRequest;
import com.gtalent.demo.responses.CreateUserResponse;
import com.gtalent.demo.responses.GetUserResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/v2/users")
@CrossOrigin("*")

public class UserV2Controller {
    private final UserRepository userRepository;

    @Autowired
    public UserV2Controller(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


    @GetMapping
    public ResponseEntity<List<GetUserResponse>> getAllUsers() {
        List<User> users = userRepository.findAll();
        return ResponseEntity.ok(users.stream().map(GetUserResponse::new).toList());
    }

    @GetMapping({"/{id}"})
    public  ResponseEntity<GetUserResponse>getUserById(@PathVariable int id){
        Optional <User> user = userRepository.findById(id);
        GetUserResponse response =new GetUserResponse(user.get());
        return ResponseEntity.ok(response);
    }


    @PutMapping("/{id}")
    public ResponseEntity<GetUserResponse>updateUserById(@PathVariable int id ,@RequestBody UpdateUserRequest request){
        Optional <User> user = userRepository.findById(id);
        User updateUser =user.get();
        updateUser.setUsername(request.getUserName());
        User savedUser =userRepository.save(updateUser);
        GetUserResponse response =new GetUserResponse(savedUser);
        return  ResponseEntity.ok(response);
    }
    //user物件裡面沒有ID所以程式會知道是create
    @PostMapping
    public ResponseEntity<GetUserResponse>createUser(@RequestBody CreateUserRequest request){
        User user =new User();
        user.setUsername(request.getUsername());
        user.setEmail(request.getEmail());
        User savedUser =userRepository.save(user);
        GetUserResponse response =new GetUserResponse(user);
        return  ResponseEntity.ok(response);
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<GetUserResponse>deleteUserById(@PathVariable int id){
        Optional <User> user = userRepository.findById(id);
        userRepository.delete(user.get());
        return ResponseEntity.noContent().build();
    }


}
