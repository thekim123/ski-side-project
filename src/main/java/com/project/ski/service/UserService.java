package com.project.ski.service;

import com.project.ski.web.dto.JoinRespDto;
import com.project.ski.domain.user.Users;
import com.project.ski.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

}
