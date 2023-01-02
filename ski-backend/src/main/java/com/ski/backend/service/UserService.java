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


        if (!dto.getUsername().equals(userEntity.getUsername())) {
            throw new IllegalArgumentException("타인의 정보를 수정하지 마세요");
        }

        userEntity.setPassword(bCryptPasswordEncoder.encode(dto.getPassword()));
        userEntity.setGender(Gender.valueOf(dto.getGender()));
        userEntity.setAgeGrp(AgeGrp.valueOf(dto.getAgeGrp()));
        userEntity.setNickname(dto.getNickname());
        userEntity.setEmail(dto.getEmail());
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

    @Transactional(readOnly = true)
    public List<?> getChatlist(Authentication authentication){
        User principal = getUserFromPrincipal(authentication);

        return null;
    }

    public User getUserFromPrincipal(Authentication authentication) {
        PrincipalDetails principalDetails = (PrincipalDetails) authentication.getPrincipal();
        return principalDetails.getUser();
    }
}
