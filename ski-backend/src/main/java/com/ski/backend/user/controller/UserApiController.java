package com.ski.backend.user.controller;

import com.ski.backend.config.auth.PrincipalDetails;
import com.ski.backend.user.service.AuthService;
import com.ski.backend.user.service.UserService;
import com.ski.backend.common.CmRespDto;
import com.ski.backend.user.dto.UserDto;
import com.ski.backend.user.dto.UserUpdateDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/user")
public class UserApiController {

    private final AuthService authService;
    private final UserService userService;

    @PostMapping("join")
    public ResponseEntity<?> join(@RequestBody UserDto dto) {
        authService.join(dto);
        return new ResponseEntity<>(new CmRespDto<>(1, "회원가입완료", null), HttpStatus.OK);
    }

    @PostMapping("login")
    public PrincipalDetails login(Authentication authentication) {
        return authService.userLogin(authentication);
    }

    @GetMapping("get")
    public CmRespDto<?> get() {
        UserDto dto = userService.getUserInformation();
        return new CmRespDto<>(1, "회원정보 조회 완료", dto);
    }

    @GetMapping("username")
    public CmRespDto<?> getUsername() {
        Optional<String> username = userService.getUsername();
        return new CmRespDto<>(1, "username 조회 완료", username);
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

    @GetMapping("nickname/{nickname}")
    public CmRespDto<?> isNicknameDuplicate(@PathVariable String nickname) {
        boolean isDuplicate = authService.isNicknameDuplicate(nickname);
        return new CmRespDto<>(1, "닉네임 중복체크 완료", isDuplicate);
    }

}
