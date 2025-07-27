package com.gtalent.demo;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Scanner;

@RestController  //為了Springboot 框架:控制器
public class HellowController {
    // 這是一個API
    @GetMapping("/hello")
    public String hello() {
        return "Hellow World";
    }

    @GetMapping("/number")
    public int number() {
        return 1314;
    }

    @PostMapping("/post")
    public String postTest() {
        return "post";
    }

    @PutMapping("/put")
    public String put() {
       return "put";
    }

}
