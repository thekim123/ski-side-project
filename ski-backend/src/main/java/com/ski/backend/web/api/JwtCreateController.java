package com.ski.backend.web.api;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.ski.backend.config.jwt.JwtProperties;
//import com.project.ski.config.oauth.GoogleUser;
import com.ski.backend.config.oauth.KakaoUser;
import com.ski.backend.config.oauth.OAuthUserInfo;
import com.ski.backend.domain.user.User;
import com.ski.backend.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.Map;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
public class JwtCreateController {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @PostMapping("/oauth/jwt/kakao")
    public String jwtCreate(@RequestBody Map<String, Object> data) {
        System.out.println("jwtCreate method is running");
        System.out.println(data);

        OAuthUserInfo kakaoUser = new KakaoUser(data);
        System.out.println(kakaoUser);

        User userEntity = userRepository.findByUsername(kakaoUser.getProvider() + "_" + kakaoUser.getProviderId());
        User userRequest = new User();
        if (userEntity == null) {
            userRequest = User.builder()
                    .username(kakaoUser.getProvider() + "_" + kakaoUser.getProviderId())
                    .password(bCryptPasswordEncoder.encode("skiproject"))
                    .roles(userRequest.getRoles())
                    .email(kakaoUser.getEmail())
                    .nickname(kakaoUser.getUsername()+"_"+UUID.randomUUID().toString())
                    .build();
            userEntity = userRepository.save(userRequest);
        }

        String jwtToken = JWT.create()
                .withSubject(userEntity.getUsername())
                .withExpiresAt(new Date(System.currentTimeMillis() + JwtProperties.EXPIRATION_TIME))
                .withClaim("id", userEntity.getId())
                .withClaim("username", userEntity.getUsername())
                .sign(Algorithm.HMAC512(JwtProperties.SECRET));

        System.out.println(jwtToken);
        return jwtToken;
    }
}
