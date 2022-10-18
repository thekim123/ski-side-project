package com.project.ski.service;

import com.project.ski.config.auth.PrincipalDetails;
import com.project.ski.domain.user.Role;
import com.project.ski.domain.user.Users;
import com.project.ski.repository.UserRepository;
import com.project.ski.web.dto.JoinRespDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
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
    public JoinRespDto userJoin(Users user) {
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        user.setRoles(Role.USER);
        Users userEntity = userRepository.save(user);
        return new JoinRespDto().toDto(userEntity);
    }

    @Transactional(readOnly = true)
    public PrincipalDetails userLogin(Authentication authentication) {
        PrincipalDetails principalDetails = (PrincipalDetails) authentication.getPrincipal();
        return principalDetails;
    }
}
