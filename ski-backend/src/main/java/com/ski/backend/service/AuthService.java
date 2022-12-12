package com.ski.backend.service;

import com.ski.backend.config.auth.PrincipalDetails;
import com.ski.backend.domain.club.AgeGrp;
import com.ski.backend.domain.club.Gender;
import com.ski.backend.domain.user.Role;
import com.ski.backend.domain.user.User;
import com.ski.backend.repository.UserRepository;
import com.ski.backend.web.dto.UserDto;
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
    public UserDto join(UserDto dto) {
        User user = new User();
        user.setUsername(dto.getUsername());
        user.setPassword(bCryptPasswordEncoder.encode(dto.getPassword()));
        user.setGender(Gender.valueOf(dto.getGender()));
        user.setAgeGrp(AgeGrp.valueOf(dto.getAgeGrp()));
        user.setRoles(Role.ROLE_USER);
        user.setEmail(dto.getEmail());
        user.setNickname(dto.getNickname());
        userRepository.save(user);
        return new UserDto().toDto(user);
    }

    @Transactional(readOnly = true)
    public PrincipalDetails userLogin(Authentication authentication) {
        PrincipalDetails principalDetails = (PrincipalDetails) authentication.getPrincipal();
        return principalDetails;
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
