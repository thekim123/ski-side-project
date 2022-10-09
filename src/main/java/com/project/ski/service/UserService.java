package com.project.ski.service;

import com.project.ski.web.dto.JoinRespDto;
import com.project.ski.web.dto.LoginRequestDto;
import com.project.ski.domain.user.User;
import com.project.ski.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Transactional
    public JoinRespDto userJoin(User user) {
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        user.setRoles("ROLE_USER");
        User userEntity = userRepository.save(user);
        return new JoinRespDto().toDto(userEntity);
    }

    @Transactional
    public void userUpdate() {

    }

    @Transactional
    public void userDelete() {

    }

    @Transactional(readOnly = true)
    public void userFindAll() {

    }

    @Transactional(readOnly = true)
    public void userFind() {

    }
}
