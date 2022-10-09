package com.project.ski.config;

import com.project.ski.config.auth.PrincipalDetails;
import com.project.ski.domain.user.User;
import com.project.ski.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class OAuth2DetailsService extends DefaultOAuth2UserService {
    private final UserRepository userRepository;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2User oAuth2User = super.loadUser(userRequest);

        Map<String, Object> userInfo = oAuth2User.getAttributes();
        String password = new BCryptPasswordEncoder().encode(UUID.randomUUID().toString());
        String username = "kakaotalk_"+userInfo.get("id");

        User userEntity = userRepository.findByUsername(username);

        if(userEntity==null){
            User user = User.builder()
                    .username(username)
                    .password(password)
                    .build();
            return new PrincipalDetails(userRepository.save(user), oAuth2User.getAttributes());
        } else{
            return new PrincipalDetails(userEntity, oAuth2User.getAttributes());
        }

    }
}
