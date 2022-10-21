package com.project.ski.service;

import com.project.ski.config.auth.PrincipalDetails;
import com.project.ski.domain.user.User;
import com.project.ski.repository.UserRepository;
import com.project.ski.web.dto.UserRespDto;
import com.project.ski.web.dto.UserUpdateDto;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;


@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Value("${file.path}")
    private String uploadFolder;

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
        userEntity.setRoles(dto.getRoles());
    }

    @Transactional(readOnly = true)
    public UserRespDto get(Authentication authentication) {
        User user = getUserFromPrincipal(authentication);
        return new UserRespDto().toDto(user);

    }

    @Transactional
    public void updateProfileImage(Authentication authentication, MultipartFile profileImageFile) {
        User principal = getUserFromPrincipal(authentication);
        long principalId = principal.getId();

        UUID uuid = UUID.randomUUID();
        String imageFileName = uuid + "_" + profileImageFile.getOriginalFilename();
        Path imageFilePath = Paths.get(uploadFolder + imageFileName);
        try {
            Files.write(imageFilePath, profileImageFile.getBytes());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        User userEntity = userRepository.findById(principalId).orElseThrow(() -> {
            return new IllegalArgumentException("사용자를 찾을 수 없습니다.");
        });
        userEntity.setProfileImageUrl(imageFileName);
    }

    public User getUserFromPrincipal(Authentication authentication) {
        PrincipalDetails principalDetails = (PrincipalDetails) authentication.getPrincipal();
        return principalDetails.getUser();
    }
}
