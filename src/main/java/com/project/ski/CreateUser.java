package com.project.ski;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.project.ski.config.jwt.JwtProperties;
import com.project.ski.domain.user.Role;
import com.project.ski.domain.user.User;
import com.project.ski.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Date;

@Component
@RequiredArgsConstructor
public class CreateUser {

    private final UserRepository userRepository;

    @PostConstruct
    public void createUserAndGenToken() {

        User user = User.builder()
                .email("test@test.com")
                .roles(Role.ROLE_USER)
                .username("test")
                .password("test")
                .build();
        if (userRepository.findByUsername(user.getUsername())==null) {
            userRepository.save(user);
        }
        String jwtToken = JWT.create()
                .withSubject(user.getUsername())
                .withExpiresAt(new Date(System.currentTimeMillis() + JwtProperties.EXPIRATION_TIME))
                .withClaim("id", user.getId())
                .withClaim("username", user.getUsername())
                .sign(Algorithm.HMAC512(JwtProperties.SECRET));

        System.out.println("jwtToken ============================= " + jwtToken);
    }
}
