package com.skiproject.sseserver.config;


public interface JwtProperties {
    String SECRET = "your-secure-secret-key-here-should-be-at-least-256-bits";
    int EXPIRATION_TIME = 864000000;
    String TOKEN_PREFIX = "Bearer ";
    String HEADER_STRING = "Authorization";
}
