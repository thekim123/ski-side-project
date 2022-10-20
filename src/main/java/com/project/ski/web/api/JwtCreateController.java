package com.project.ski.web.api;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.project.ski.config.jwt.JwtProperties;
//import com.project.ski.config.oauth.GoogleUser;
import com.project.ski.config.oauth.GoogleUser;
import com.project.ski.config.oauth.OAuthUserInfo;
import com.project.ski.domain.user.Role;
import com.project.ski.domain.user.User;
import com.project.ski.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.Map;

@RestController
@RequiredArgsConstructor
public class JwtCreateController {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @PostMapping("/oauth/jwt/google")
    public String jwtCreate(@RequestBody Map<String, Object> data) {
        System.out.println("jwtCreate method is running");
        System.out.println(data.get("profileObj"));
        OAuthUserInfo googleUser = new GoogleUser((Map<String, Object>) data.get("profileObj"));
        System.out.println(googleUser);



        User userEntity = userRepository.findByUsername(googleUser.getProvider() + "_" + googleUser.getProviderId());
        User userRequest = new User();
        if (userEntity == null) {
            userRequest = User.builder()
                    .username(googleUser.getProvider()+"_"+googleUser.getProviderId())
                    .password(bCryptPasswordEncoder.encode("skiproject"))
                    .roles(userRequest.getRoles())
                    .build();
            userEntity = userRepository.save(userRequest);
        }

        String jwtToken = JWT.create()
                .withSubject(userEntity.getUsername())
                .withExpiresAt(new Date(System.currentTimeMillis()+ JwtProperties.EXPIRATION_TIME))
                .withClaim("id", userEntity.getId())
                .withClaim("username", userEntity.getUsername())
                .sign(Algorithm.HMAC512(JwtProperties.SECRET));

        return jwtToken;
    }
}
