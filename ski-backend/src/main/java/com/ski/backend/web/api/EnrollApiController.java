package com.ski.backend.web.api;

import com.ski.backend.config.auth.PrincipalDetails;
import com.ski.backend.service.EnrollService;
import com.ski.backend.web.dto.CmRespDto;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/enroll/")
public class EnrollApiController {

    private final EnrollService enrollService;

    @PostMapping("{clubId}")
    public CmRespDto<Long> enroll(@PathVariable long clubId, Authentication auth) {

        PrincipalDetails principal = (PrincipalDetails) auth.getPrincipal();
        long userId = principal.getUser().getId();
        Long enrollId = enrollService.enroll(userId, clubId);

        return new CmRespDto<>(1, "가입 성공", enrollId);
    }
}
