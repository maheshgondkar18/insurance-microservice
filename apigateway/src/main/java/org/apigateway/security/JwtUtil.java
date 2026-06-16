package org.apigateway.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import org.apigateway.enums.Role;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.security.Key;

@Component
public class JwtUtil {

    @Value("${jwt.secret}")
    private String secret;

    private Key getSigningKey(){
        return Keys.hmacShaKeyFor(secret.getBytes());
    }

    @PostConstruct
    public void debugKeyLength() {
        System.out.println("GATEWAY JWT SECRET LENGTH = " + secret.getBytes().length);
    }
    public void validateToken(String token) {
        Jwts.parserBuilder()
                .setSigningKey(getSigningKey())
                .build()
                .parseClaimsJws(token);
    }


    private Claims extractClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(getSigningKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    public Long extractUserId(String token) {
        return extractClaims(token).get("userId", Long.class);
    }

    public String extractEmail(String token) {
        return extractClaims(token).getSubject();
    }

    public Role extractRole(String token) {
        String role = extractClaims(token).get("role", String.class);
        return Role.valueOf(role);   // ✅ convert String → Enum
    }

    @PostConstruct
    public void debugKey() {
        System.out.println("JWT SECRET LENGTH = " + secret.getBytes().length);
    }
    @PostConstruct
    public void debug() {
        System.out.println("SECRET VALUE >>> " + secret);
        System.out.println("SECRET LENGTH >>> " + secret.getBytes().length);
    }





}
