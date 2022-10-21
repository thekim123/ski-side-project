package com.project.ski.web.api;

import com.project.ski.config.auth.PrincipalDetails;
import com.project.ski.service.AuthService;
import com.project.ski.service.UserService;
import com.project.ski.web.dto.CmRespDto;
import com.project.ski.web.dto.UserRespDto;
import com.project.ski.domain.user.User;
import com.project.ski.web.dto.UserUpdateDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/user")
public class UserApiController {

    private final AuthService authService;
    private final UserService userService;

    @PostMapping("join")
    public ResponseEntity<?> join(@RequestBody User user) {
        UserRespDto joinDto = authService.join(user);
        return new ResponseEntity<>(new CmRespDto<>(1, "회원가입완료", joinDto), HttpStatus.OK);
    }

    @PostMapping("login")
    public PrincipalDetails login(Authentication authentication) {
        PrincipalDetails principalDetails = authService.userLogin(authentication);
        return principalDetails;
    }

    @PostMapping("get")
    public CmRespDto<?> get(Authentication authentication) {
        UserRespDto dto = userService.get(authentication);
        return new CmRespDto<>(1, "회원정보 조회 완료", dto);
    }

    @PutMapping("update")
    public CmRespDto<?> update(@RequestBody UserUpdateDto dto, Authentication authentication) {
        userService.update(dto, authentication);
        return new CmRespDto<>(1, "회원정보 수정 완료", null);
    }

    @DeleteMapping("delete")
    public CmRespDto<?> delete(Authentication authentication) {
        authService.delete(authentication);
        return new CmRespDto<>(1, "회원탈퇴 완료", null);
    }

    @PostMapping("update/profile/")
    public CmRespDto<?> profileImageUrlUpdate(Authentication authentication, MultipartFile profileImageFile) {
        userService.updateProfileImage(authentication, profileImageFile);
        return new CmRespDto<>(1, "프로필 사진 업데이트 완료", null);
    }
}
