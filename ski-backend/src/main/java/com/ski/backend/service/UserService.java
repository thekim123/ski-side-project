package com.ski.backend.service;

import com.ski.backend.config.auth.PrincipalDetails;
import com.ski.backend.domain.common.AgeGrp;
import com.ski.backend.domain.club.Gender;
import com.ski.backend.domain.user.ChatRoom;
import com.ski.backend.domain.user.Role;
import com.ski.backend.domain.user.User;
import com.ski.backend.repository.UserRepository;
import com.ski.backend.web.dto.UserDto;
import com.ski.backend.web.dto.UserUpdateDto;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


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
        userEntity.setAge(dto.getAge());
        userEntity.setNickname(dto.getNickname());
        if (dto.getRoles() != null) {
            userEntity.setRoles(Role.valueOf(dto.getRoles()));
        }

    }

    @Transactional(readOnly = true)
    public UserDto get(Authentication authentication) {
        User user = getUserFromPrincipal(authentication);
        UserDto dto = new UserDto().toDto(user);
        return dto;
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
