package com.skiproject.sseserver.config;


public interface JwtProperties {
    String SECRET = "your-secure-secret-key-here-should-be-at-least-256-bits";
    String TOKEN_PREFIX = "Bearer ";
}
