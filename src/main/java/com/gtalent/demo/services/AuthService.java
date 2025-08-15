package com.gtalent.demo.services;

import com.gtalent.demo.models.User;
import com.gtalent.demo.repositories.UserRepository;
import com.gtalent.demo.requests.LoginRequest;
import com.gtalent.demo.requests.RegistrationRequest;
import com.gtalent.demo.responses.AuthResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private  JwtService jwtService;

    public AuthResponse register(RegistrationRequest request) {
        User user = new User();
        user.setUsername(request.getUsername());
        user.setEmail(request.getEmail());
        user.setPassword(request.getPassword());
        userRepository.save(user);
        String jwtToken = jwtService.generateToken(user);
        return new AuthResponse(jwtToken);

    }
    public AuthResponse auth(LoginRequest request){
        Optional<User> userOptional =userRepository.findByUsername(request.getUsername());
        if(userOptional.isPresent()){
            User user = userOptional.get();
            if(request.getPassword().equals(user.getPassword())){
                String jwtToken = jwtService.generateToken(user);
                return  new AuthResponse(jwtToken);
            }
        }
        throw new RuntimeException("無效憑證");
    }
}
