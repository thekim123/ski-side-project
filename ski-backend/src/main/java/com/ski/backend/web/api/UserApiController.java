package com.ski.backend.web.api;

import com.ski.backend.config.auth.PrincipalDetails;
import com.ski.backend.service.AuthService;
import com.ski.backend.service.UserService;
import com.ski.backend.web.dto.CmRespDto;
import com.ski.backend.web.dto.UserRespDto;
import com.ski.backend.domain.user.User;
import com.ski.backend.web.dto.UserUpdateDto;
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

    @GetMapping("get")
    public CmRespDto<?> get(Authentication authentication) {
        User user = userService.get(authentication);
        return new CmRespDto<>(1, "회원정보 조회 완료", user);
    }

    @GetMapping("username")
    public CmRespDto<?> getUsername(Authentication authentication) {
        String username = userService.getUsername(authentication);
        return new CmRespDto<>(1, "회원정보 조회 완료", username);
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
}
