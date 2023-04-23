package com.ski.backend.user.service;

import com.ski.backend.config.AuditorProvider;
import com.ski.backend.config.auth.PrincipalDetails;
import com.ski.backend.club.entity.Gender;
import com.ski.backend.user.entity.Role;
import com.ski.backend.user.entity.User;
import com.ski.backend.handler.ex.CustomApiException;
import com.ski.backend.user.repository.UserRepository;
import com.ski.backend.user.vo.UserVo;
import com.ski.backend.user.dto.UserDto;
import com.ski.backend.user.dto.UserUpdateDto;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Transactional
    public void update(UserUpdateDto dto, Authentication authentication) {
        User userEntity = ((PrincipalDetails) authentication.getPrincipal()).getUser();
        userEntity = userRepository.findById(userEntity.getId()).orElseThrow(() -> {
            throw new EntityNotFoundException("존재하지 않는 회원입니다.");
        });

        Role userRole = Role.ROLE_USER;
        if (!dto.getAgreement()) {
            userRole = Role.ROLE_GUEST;
        }

        if (dto.getAge() < 20) {
            throw new CustomApiException("20세 미만은 가입할 수 없습니다.");
        }

        UserVo vo = UserVo.builder()
                .agreement(dto.getAgreement())
                .password(bCryptPasswordEncoder.encode(dto.getPassword()))
                .gender(Gender.valueOf(dto.getGender()))
                .role(userRole)
                .nickname(dto.getNickname())
                .age(dto.getAge())
                .username(dto.getUsername())
                .email(dto.getEmail())
                .build();

        userEntity.updateUser(vo);
    }


    @Transactional(readOnly = true)
    public Optional<String> getUsername() {
        AuditorProvider ad = new AuditorProvider();
        return ad.getCurrentAuditor();
    }

    @Transactional(readOnly = true)
    public UserDto getUserInformation() {
        AuditorProvider auditorProvider = new AuditorProvider();
        String username = auditorProvider.getCurrentAuditor().orElseThrow(() -> {
            throw new AccessDeniedException("로그인하고 이용해 주세요.");
        });

        User loginUser = userRepository.findByUsername(username).orElseThrow(() -> {
            throw new EntityNotFoundException("존재하지 않는 회원입니다.");
        });

        UserDto dto = new UserDto();
        return dto.transformUserToDto(loginUser);

    }

}
