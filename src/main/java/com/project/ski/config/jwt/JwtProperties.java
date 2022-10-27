package com.project.ski.config.jwt;


import java.security.Key;
import java.util.Base64;

public interface JwtProperties {
    String SECRET = "skiproject";
    int EXPIRATION_TIME = 864000000;
    String TOKEN_PREFIX = "Bearer ";
    String HEADER_STRING = "Authorization";
}
