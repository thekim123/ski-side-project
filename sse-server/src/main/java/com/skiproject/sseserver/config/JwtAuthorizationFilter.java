package com.skiproject.sseserver.config;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
//import io.jsonwebtoken.Claims;
//import io.jsonwebtoken.Jws;
//import io.jsonwebtoken.JwtException;
import com.auth0.jwt.exceptions.JWTDecodeException;
import lombok.RequiredArgsConstructor;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;


@Component
@RequiredArgsConstructor
public class JwtAuthorizationFilter implements WebFilter {
    private final Environment env;

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
        String token = exchange.getRequest().getHeaders().getFirst(HttpHeaders.AUTHORIZATION);
        if (token == null || !token.startsWith(JwtProperties.TOKEN_PREFIX)) {
            exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
            return exchange.getResponse().setComplete();
        }
        token = token.substring(7);

        String username;
        try {
            username = JWT.require(Algorithm.HMAC512(JwtProperties.SECRET)).build().verify(token)
                    .getClaim("username").asString();
        } catch (JWTDecodeException e) {
            e.printStackTrace();
            exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
            return exchange.getResponse().setComplete();
        }

        if (username == null || username.isEmpty()) {
            exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
            return exchange.getResponse().setComplete();
        }

        return chain.filter(exchange);
    }

    //    @Override
//    public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
//        String token = exchange.getRequest().getHeaders().getFirst(HttpHeaders.AUTHORIZATION);
//        if (token == null || !token.startsWith("Bearer ")) {
//            exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
//            return exchange.getResponse().setComplete();
//        }
//
//        token = token.substring(7); // Remove "Bearer " prefix
//
//        try {
//            // Parse and validate JWT token
//            Jws<Claims> parsedToken = JwtTokenUtil.parseToken(token);
//            System.out.println(parsedToken);
//            String username = parsedToken.getBody().getSubject();
//            System.out.println(username);
//            // Set userId as a request attribute
//            exchange.getAttributes().put("username", username);
//
//        } catch (JwtException e) {
//            e.printStackTrace();
//            exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
//            return exchange.getResponse().setComplete();
//        }
//
//        return chain.filter(exchange);
//    }
}


