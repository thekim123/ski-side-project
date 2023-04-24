package com.ski.backend.config.jwt;


import java.security.Key;
import java.util.Base64;

public interface JwtProperties {
    String SECRET = "your-secure-secret-key-here-should-be-at-least-256-bits";
    int EXPIRATION_TIME = 864000000;
    String TOKEN_PREFIX = "Bearer ";
    String HEADER_STRING = "Authorization";
}
