package com.gtalent.demo;

import com.gtalent.demo.requests.CreateUserRequest;
import com.gtalent.demo.responses.CreateUserResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/users")

public class UserV1Controller {
    private final JdbcTemplate jbdcTemplate;
    //注入
    @Autowired
    public UserV1Controller(JdbcTemplate jbdcTemplate){
        this.jbdcTemplate = jbdcTemplate;
    }

    @PostMapping
    public ResponseEntity<CreateUserResponse>creatUser(@RequestBody CreateUserRequest request){
        String sql = "intsert into users (username,email) values(?,?) ";
        //因為update 回傳 int,update了幾行
        int rowsAffected = jbdcTemplate.update(sql,request.getUsername(),request.getEmail());
        if(rowsAffected > 0){
            CreateUserResponse response = new CreateUserResponse(request.getUsername());
            return  new ResponseEntity<>(response, HttpStatus.CREATED);
        }
        return ResponseEntity.badRequest().build();
    }
}
