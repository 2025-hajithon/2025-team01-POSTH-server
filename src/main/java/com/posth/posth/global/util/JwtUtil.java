package com.posth.posth.global.util;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;

@Component
public class JwtUtil {

    @Value("${jwt.secret}")
    private String jwtSecret;

    private final Long EXPIRATION_TIME = 2*60L;

    public String generateToken(String username) {
        Date now = new Date();
        Date expireDateTime = new Date(now.getTime() + EXPIRATION_TIME);

        Key key = Keys.hmacShaKeyFor(jwtSecret.getBytes());

        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(now)
                .setExpiration(expireDateTime)
                .signWith(key, SignatureAlgorithm.HS256) // 서명
                .compact();
    }
}
