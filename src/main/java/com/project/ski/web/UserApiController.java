package com.project.ski.web;

import com.project.ski.config.auth.PrincipalDetails;
import com.project.ski.web.dto.CMRespDto;
import com.project.ski.web.dto.JoinRespDto;
import com.project.ski.domain.user.User;
import com.project.ski.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping()
public class UserApiController {

    private final UserService userService;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @PostMapping("user/join")
    public ResponseEntity<?> join(@RequestBody User user) {
        JoinRespDto joinDto = userService.userJoin(user);
        return new ResponseEntity<>(new CMRespDto<>(1, "회원가입완료", joinDto), HttpStatus.OK);
    }

    @PostMapping("user/login")
    public String login(Authentication authentication) {
        PrincipalDetails principalDetails = (PrincipalDetails) authentication.getPrincipal();
        System.out.println("principal : " + principalDetails);
        return "user";
    }

}
