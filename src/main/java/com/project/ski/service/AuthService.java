package com.project.ski.service;

import com.project.ski.config.auth.PrincipalDetails;
import com.project.ski.domain.user.Role;
import com.project.ski.domain.user.User;
import com.project.ski.repository.UserRepository;
import com.project.ski.web.dto.UserRespDto;
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
    public UserRespDto join(User user) {
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        user.setRoles(Role.ROLE_USER);
        User userEntity = userRepository.save(user);
        return new UserRespDto().toDto(userEntity);
    }

    @Transactional(readOnly = true)
    public PrincipalDetails userLogin(Authentication authentication) {
        PrincipalDetails principalDetails = (PrincipalDetails) authentication.getPrincipal();
        return principalDetails;
    }

    @Transactional
    public void delete(Authentication authentication) {
        User user = getUserFromPrincipal(authentication);
        User userEntity = userRepository.findById(user.getId()).orElseThrow(()->{
            return new IllegalArgumentException("존재하지 않는 회원번호입니다.");
        });
        userRepository.delete(userEntity);
    }

    public User getUserFromPrincipal(Authentication authentication) {
        PrincipalDetails principalDetails = (PrincipalDetails) authentication.getPrincipal();
        return principalDetails.getUser();
    }

}
