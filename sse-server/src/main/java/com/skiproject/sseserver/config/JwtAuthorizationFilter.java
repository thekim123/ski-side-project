package com.skiproject.sseserver.config;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTDecodeException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;


/**
 * @author thekim123
 * @apiNote ski-backend 서버에서의 인증 결과로 받은 jwt 를 Authorization 해주는 필터입니다.
 * @since 2023.04.24
 */
@Component
@RequiredArgsConstructor
public class JwtAuthorizationFilter implements WebFilter {

    /**
     * @param exchange the current server exchange
     * @param chain    provides a way to delegate to the next filter
     * @apiNote jwt 인가 해주는 메서드
     * @since 2023.04.24
     */
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
        String token = exchange.getRequest().getHeaders().getFirst(HttpHeaders.AUTHORIZATION);
        if (isValidAuthorizationHeader(token)) {
            return responseWhenUnauthorized(exchange);
        }
        token = token.substring(7);

        String username;
        try {
            username = getUsernameFromJWT(token);
        } catch (JWTDecodeException e) {
            e.printStackTrace();
            return responseWhenUnauthorized(exchange);
        }

        if (username == null || username.isEmpty()) {
            return responseWhenUnauthorized(exchange);
        }

        return chain.filter(exchange);
    }

    /**
     * @param token authorization header
     * @return 유효한 authorization 인지 여부
     */
    private static boolean isValidAuthorizationHeader(String token) {
        return token == null || !token.startsWith(JwtProperties.TOKEN_PREFIX);
    }

    /**
     * @return 권한이 없을 때 UnAuthorized HTTP Status 를 반환함
     */
    private static Mono<Void> responseWhenUnauthorized(ServerWebExchange exchange) {
        exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
        return exchange.getResponse().setComplete();
    }

    /**
     * @param token 파싱한 jwt 토큰
     * @return username 을 반환함
     */
    private String getUsernameFromJWT(String token) {
        String username;
        username = JWT.require(Algorithm.HMAC512(JwtProperties.SECRET)).build().verify(token)
                .getClaim("username").asString();
        return username;
    }

}


