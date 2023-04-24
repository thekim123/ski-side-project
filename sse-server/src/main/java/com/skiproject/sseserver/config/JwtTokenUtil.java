//package com.skiproject.sseserver.config;
//
//import io.jsonwebtoken.Claims;
//import io.jsonwebtoken.Jws;
//import io.jsonwebtoken.Jwts;
//import io.jsonwebtoken.SignatureAlgorithm;
//import io.jsonwebtoken.security.Keys;
//import lombok.RequiredArgsConstructor;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.core.env.Environment;
//import org.springframework.stereotype.Component;
//
//import java.nio.charset.StandardCharsets;
//import java.security.Key;
//import java.util.Objects;
//
//
//@Component
//@RequiredArgsConstructor
//public class JwtTokenUtil {
//
//    private static final String SECRET_KEY = "your-secure-secret-key-here-should-be-at-least-256-bits";
//
//    public static String createToken(String userId) {
//        return Jwts.builder()
//                .setSubject(userId)
//                .signWith(Keys.hmacShaKeyFor(SECRET_KEY.getBytes(StandardCharsets.UTF_8)))
//                .compact();
//    }
//
//    public static Jws<Claims> parseToken(String token) {
//        Jws<Claims> jwts = Jwts.parserBuilder()
//                .setSigningKey(Keys.hmacShaKeyFor(SECRET_KEY.getBytes(StandardCharsets.UTF_8)))
//                .build()
//                .parseClaimsJws(token);
//        System.out.println(jwts);
//        return jwts;
//    }
//}
