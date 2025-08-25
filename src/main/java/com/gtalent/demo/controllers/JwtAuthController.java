package com.gtalent.demo.controllers;

import com.gtalent.demo.requests.LoginRequest;
import com.gtalent.demo.requests.RegistrationRequest;
import com.gtalent.demo.responses.AuthResponse;
import com.gtalent.demo.services.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/jwt")
@CrossOrigin("*")
@Tag(name ="JWT驗證",description = "提供使用者登入或者註冊")
public class JwtAuthController {

    @Autowired
    private AuthService authService;

    @Operation(summary ="JWT註冊" ,description ="1.username不能重複 2.密碼長度要超過8字元 3.必須提供用戶角色，以ROLE_ 開頭(ROLE_USER)")
    @ApiResponses(value ={
            @ApiResponse(responseCode = "200",description = "操作成功"),
            @ApiResponse(responseCode = "401",description = "資料格式不正確"),
            @ApiResponse(responseCode = "403",description = "權限不足")
    })
    @PostMapping("/register")
    public ResponseEntity<AuthResponse> register(@RequestBody RegistrationRequest request) {
        if(request.getPassword().length() <8 || request.getRole().startsWith("ROLE_")){
            //401
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        //200
        return ResponseEntity.ok(authService.register(request));
    }

    @Operation(summary ="JWT登入" ,description ="返回token")
    @PostMapping("/auth")
    public ResponseEntity<AuthResponse>auth(@RequestBody LoginRequest request){
        return ResponseEntity.ok(authService.auth(request));
        }

}
