package com.ski.backend.service;

import com.ski.backend.config.auth.PrincipalDetails;
import com.ski.backend.domain.club.Gender;
import com.ski.backend.domain.user.Role;
import com.ski.backend.domain.user.User;
import com.ski.backend.handler.ex.CustomApiException;
import com.ski.backend.repository.UserRepository;
import com.ski.backend.web.dto.UserDto;
import com.ski.backend.web.dto.UserUpdateDto;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Transactional
    public void update(UserUpdateDto dto, Authentication authentication) {
        User userEntity = getUserFromPrincipal(authentication);
        userEntity = userRepository.findById(userEntity.getId()).orElseThrow(() -> {
            return new IllegalArgumentException("존재하지 않는 회원번호입니다.");
        });


        userEntity.setPassword(bCryptPasswordEncoder.encode(dto.getPassword()));
        userEntity.setGender(Gender.valueOf(dto.getGender()));

        if (dto.getAge() < 20) {
            throw new CustomApiException("20세 미만은 가입할 수 없습니다.");
        }

        userEntity.setAge(dto.getAge());
        userEntity.setNickname(dto.getNickname());
        if (dto.getRoles() != null) {
            userEntity.setRoles(Role.valueOf(dto.getRoles()));
        }

        userEntity.setAgreement(dto.getAgreement());
        if (!dto.getAgreement()) {
            userEntity.setRoles(Role.ROLE_GUEST);
        }
    }

    @Transactional(readOnly = true)
    public UserDto get(Authentication authentication) {
        User user = getUserFromPrincipal(authentication);
        return new UserDto().toDto(user);
    }

    @Transactional(readOnly = true)
    public String getUsername(Authentication authentication) {
        User user = getUserFromPrincipal(authentication);
        return user.getUsername();
    }

    public User getUserFromPrincipal(Authentication authentication) {
        PrincipalDetails principalDetails = (PrincipalDetails) authentication.getPrincipal();
        return principalDetails.getUser();
    }
}
