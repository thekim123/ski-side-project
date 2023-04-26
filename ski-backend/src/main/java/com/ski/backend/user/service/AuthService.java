package com.ski.backend.user.service;

import com.ski.backend.config.auth.PrincipalDetails;
import com.ski.backend.club.entity.Gender;
import com.ski.backend.user.entity.Role;
import com.ski.backend.user.entity.User;
import com.ski.backend.user.repository.UserRepository;
import com.ski.backend.user.dto.UserDto;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Transactional
    public void join(UserDto dto) {
        User user = User.builder()
                .username(dto.getUsername())
                .password(bCryptPasswordEncoder.encode(dto.getPassword()))
                .gender(Gender.valueOf(dto.getGender()))
                .age(dto.getAge())
                .roles(Role.ROLE_GUEST)
                .nickname(dto.getNickname())
                .email(dto.getEmail())
                .build();
        userRepository.save(user);
    }

    @Transactional(readOnly = true)
    public PrincipalDetails userLogin(Authentication authentication) {
        return (PrincipalDetails) authentication.getPrincipal();
    }

    @Transactional
    public void delete(Authentication authentication) {
        User user = getUserFromPrincipal(authentication);
        User userEntity = userRepository.findById(user.getId()).orElseThrow(() -> {
            return new IllegalArgumentException("존재하지 않는 회원번호입니다.");
        });
        userRepository.delete(userEntity);
    }

    public User getUserFromPrincipal(Authentication authentication) {
        PrincipalDetails principalDetails = (PrincipalDetails) authentication.getPrincipal();
        return principalDetails.getUser();
    }

    @Transactional(readOnly = true)
    public boolean isNicknameDuplicate(String nickname) {
        return userRepository.existsByNickname(nickname);
    }

}
