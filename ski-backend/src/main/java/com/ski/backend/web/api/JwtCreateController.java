package com.ski.backend.web.api;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.ski.backend.config.jwt.JwtProperties;
import com.ski.backend.config.oauth.KakaoUser;
import com.ski.backend.config.oauth.OAuthUserInfo;
import com.ski.backend.domain.user.Role;
import com.ski.backend.domain.user.User;
import com.ski.backend.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.Map;

// TODO: 이건 어떻게 리팩토링할건지
@RestController
@RequiredArgsConstructor
public class JwtCreateController {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @PostMapping("/oauth/jwt/kakao")
    public String jwtCreate(@RequestBody Map<String, Object> data) {
        OAuthUserInfo kakaoUser = new KakaoUser(data);

        User userEntity = userRepository.findByUsername(kakaoUser.getProvider() + "_" + kakaoUser.getProviderId());
        User userRequest;
        if (userEntity == null) {
            userRequest = User.builder()
                    .username(kakaoUser.getProvider() + "_" + kakaoUser.getProviderId())
                    .password(bCryptPasswordEncoder.encode("skiproject"))
                    .roles(Role.ROLE_GUEST)
                    .build();
            userEntity = userRepository.save(userRequest);
        }

        return JWT.create()
                .withSubject(userEntity.getUsername())
                .withExpiresAt(new Date(System.currentTimeMillis() + JwtProperties.EXPIRATION_TIME))
                .withClaim("id", userEntity.getId())
                .withClaim("username", userEntity.getUsername())
                .sign(Algorithm.HMAC512(JwtProperties.SECRET));
    }

}
