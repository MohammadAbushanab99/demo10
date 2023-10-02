package com.example.demo10;

import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;

@Service
public class TokenService {

    private final SecretKey secretKey = Keys.secretKeyFor( SignatureAlgorithm.HS256); // Replace with your secret key

    public String generateAuthToken() {
        String token = TokenGenerator.generateToken(secretKey, 3600000); // 1 hour expiration
        return token;
    }
}
