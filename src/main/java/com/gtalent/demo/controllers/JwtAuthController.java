package com.gtalent.demo.controllers;

import com.gtalent.demo.requests.LoginRequest;
import com.gtalent.demo.requests.RegistrationRequest;
import com.gtalent.demo.responses.AuthResponse;
import com.gtalent.demo.services.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/jwt")
@CrossOrigin("*")
public class JwtAuthController {
    @Autowired
    private AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<AuthResponse> register(@RequestBody RegistrationRequest request) {
        return ResponseEntity.ok(authService.register(request));
    }

    @PostMapping("/auth")
    public ResponseEntity<AuthResponse>auth(@RequestBody LoginRequest request){
        return ResponseEntity.ok(authService.auth(request));
        }

}
