package com.gtalent.demo.configs;

import com.gtalent.demo.models.User;
import com.gtalent.demo.repositories.UserRepository;
import com.gtalent.demo.services.JwtService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Component
public class JwtAuthFilter extends OncePerRequestFilter {
    @Autowired
    private JwtService jwtService;
    @Autowired
    private UserRepository userRepository;

    @Override
    protected  void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException{
        String authHeader =request.getHeader("Authrization");
        if(authHeader == null || !authHeader.startsWith("Bearer ")){
            filterChain.doFilter(request,response);
            return;
        }
        String jwtToken = authHeader.substring(7);
        String username = JwtService.getUsernameFromToken(jwtToken);

        if(username != null && SecurityContextHolder.getContext().getAuthentication() == null){
           Optional<User>  user = userRepository.findByUsername(username);
           if(user.isPresent()){
               List<? extends GrantedAuthority> authorities = getUserAuthorities();
               UsernamePasswordAuthenticationToken authenticationToken =new UsernamePasswordAuthenticationToken(user.get(),null);
               SecurityContextHolder.getContext().setAuthentication(authenticationToken);
           }
        }
        filterChain.doFilter(request,response);
    }

    private List<? extends GrantedAuthority> getUserAuthorities() {
        return List.of(new SimpleGrantedAuthority("ROLE_USER"));
    }

}
