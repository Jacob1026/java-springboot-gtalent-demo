package com.gtalent.demo.services;

import com.gtalent.demo.models.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;

@Service
public class JwtService {
    private long EXPIRATION_TIME;//毫秒


    public String generateToken(User user){

        return buildToken(user);
    }

    private String buildToken( User user) {
        return Jwts.builder()
                .setSubject(user.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis()+600000))
                .signWith(getKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    private static Key getKey() {
        byte[] ketByte = Decoders.BASE64.decode("dGlueXNhbWVoYW5kc29tZXlvdW5nY2FsbHJlY29yZGdpZnRpbnZlbnRlZHdpdGhvdXQ=");
        return Keys.hmacShaKeyFor(ketByte);
    }

    public static String getUsernameFromToken(String token){
        return Jwts.parserBuilder()
                .setSigningKey(getKey()).build()
                .parseClaimsJws(token)
                .getBody()
                .getSubject ( ) ;
    }

}
