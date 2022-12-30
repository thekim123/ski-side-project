package com.ski.backend.web.api;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.ski.backend.config.jwt.JwtProperties;
//import com.project.ski.config.oauth.GoogleUser;
import com.ski.backend.config.oauth.KakaoUser;
import com.ski.backend.config.oauth.OAuthUserInfo;
import com.ski.backend.domain.common.AgeGrp;
import com.ski.backend.domain.club.Gender;
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
import java.util.StringTokenizer;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
public class JwtCreateController {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @PostMapping("/oauth/jwt/kakao")
    public String jwtCreate(@RequestBody Map<String, Object> data) {
        OAuthUserInfo kakaoUser = new KakaoUser(data);

        String rawGender = getRawGender(data);
        Gender gender = getGender(rawGender);
        AgeGrp ageGrp = getAgeGrp(data);

        User userEntity = userRepository.findByUsername(kakaoUser.getProvider() + "_" + kakaoUser.getProviderId());
        User userRequest = new User();
        if (userEntity == null) {
            userRequest = User.builder()
                    .username(kakaoUser.getProvider() + "_" + kakaoUser.getProviderId())
                    .password(bCryptPasswordEncoder.encode("skiproject"))
                    .roles(Role.ROLE_USER)
                    .email(kakaoUser.getEmail())
                    .nickname(kakaoUser.getUsername() + "_" + UUID.randomUUID())
                    .gender(gender)
                    .ageGrp(ageGrp)
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

    public String getRawGender(Map<String, Object> data) {
        Map<String, Object> accountInfo = (Map<String, Object>) data.get("kakao_account");
        String result = (String) accountInfo.get("gender");
        if (result == null) {
            return "NO";
        }
        return result.toUpperCase();
    }

    public Gender getGender(String rawGender) {
        Gender result = Gender.NO;
        if ("MALE".equals(rawGender) || "MEN".equals(rawGender)) {
            result = Gender.MEN;
        }
        if ("FEMALE".equals(rawGender) || "WOMEN".equals(rawGender)) {
            result = Gender.WOMEN;
        }

        return result;
    }

    public AgeGrp getAgeGrp(Map<String, Object> data) {
        Map<String, Object> accountInfo = (Map<String, Object>) data.get("kakao_account");

        String rawAgeGrp = (String) accountInfo.get("age_range");

        StringTokenizer st = new StringTokenizer(rawAgeGrp, "~");
        int startAge = Integer.parseInt(st.nextToken());
        AgeGrp result = AgeGrp.ANY;

        if (startAge == 10) {
            result = AgeGrp.TEN;
        } else if (startAge == 20) {
            result = AgeGrp.TWENTY;
        } else if (startAge == 30) {
            result = AgeGrp.THIRTY;
        } else if (startAge == 40) {
            result = AgeGrp.FORTY;
        } else if (startAge == 50) {
            result = AgeGrp.FIFTY;
        } else if (startAge == 60) {
            result = AgeGrp.SIXTY;
        } else if (startAge == 70) {
            result = AgeGrp.SEVENTY;
        } else if (startAge == 80) {
            result = AgeGrp.EIGHTY;
        } else {
            result = AgeGrp.ANY;
        }

        return result;
    }


}
