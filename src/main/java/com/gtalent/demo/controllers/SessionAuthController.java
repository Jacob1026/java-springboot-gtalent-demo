package com.gtalent.demo.controllers;

import com.gtalent.demo.models.User;
import com.gtalent.demo.repositories.UserRepository;
import com.gtalent.demo.requests.LoginRequest;
import com.gtalent.demo.services.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;


@RestController
@RequestMapping("/session")
@CrossOrigin("*")
public class SessionAuthController {
    private final UserService userService;
    private final UserRepository userRepository;

    @Autowired
    public SessionAuthController(UserService userService, UserRepository userRepository) {
        this.userService = userService;
        this.userRepository = userRepository;
    }
    @PostMapping("/login")
    public ResponseEntity<User>login(@RequestBody LoginRequest request, HttpSession session) {
        Optional<User> user = userService.findByUserNameAndPassword(request.getUsername(), request.getPassword());
        if (user.isPresent()) {
            session.setAttribute("userId", user.get().getId());
            return ResponseEntity.ok(user.get());
        }
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }
    @GetMapping("/profile")
    public ResponseEntity<User> getProfile(HttpSession session){
        Integer userId = (Integer)session.getAttribute("userId");
        if(userId == null){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        Optional<User>user =userRepository.findById(userId);
        return ResponseEntity.ok(user.get());
    }

    @PostMapping("/logout")
    public ResponseEntity<Void>logout(HttpSession session){
        session.invalidate();
        return ResponseEntity.ok().build();
    }

    @PostMapping("/register"){

    }
}
