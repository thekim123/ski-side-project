package com.ski.backend;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.ski.backend.config.jwt.JwtProperties;
import com.ski.backend.domain.common.AgeGrp;
import com.ski.backend.domain.club.Gender;
import com.ski.backend.domain.user.Role;
import com.ski.backend.domain.user.User;
import com.ski.backend.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Date;
import java.util.List;

@Component
@RequiredArgsConstructor
public class CreateUser {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @PostConstruct
    public void createUserAndGenToken() {

        String password = bCryptPasswordEncoder.encode("password");

        User user = User.builder()
                .email("abc@abc.com")
                .roles(Role.ROLE_USER)
                .nickname("abc")
                .username("abc")
                .password(password)
                .gender(Gender.MEN)
                .ageGrp(AgeGrp.SEVENTY)
                .build();
        User user1 = User.builder()
                .email("test@abc.com")
                .roles(Role.ROLE_USER)
                .nickname("test")
                .username("test")
                .password(password)
                .gender(Gender.MEN)
                .ageGrp(AgeGrp.SEVENTY)
                .build();
        User user2 = User.builder()
                .email("project@abc.com")
                .roles(Role.ROLE_USER)
                .nickname("project")
                .username("project")
                .password(password)
                .gender(Gender.MEN)
                .ageGrp(AgeGrp.SEVENTY)
                .build();
        List<User> userList = List.of(user, user1, user2);

        userList.forEach(u -> {
            if (userRepository.findByUsername(u.getUsername()) == null) {
                userRepository.save(u);
            }
            System.out.println("jwtToken   "+u.getUsername()+" ============================= " + JWT.create()
                    .withSubject(u.getUsername())
                    .withExpiresAt(new Date(System.currentTimeMillis() + JwtProperties.EXPIRATION_TIME))
                    .withClaim("id", u.getId())
                    .withClaim("username", u.getUsername())
                    .sign(Algorithm.HMAC512(JwtProperties.SECRET))
            );


        });


    }

}
