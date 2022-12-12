package com.project.ski.web.api;

import com.project.ski.config.auth.PrincipalDetails;
import com.project.ski.service.EnrollService;
import com.project.ski.web.dto.CmRespDto;
import com.project.ski.web.dto.EnrollRespDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping("{clubId}")
    public CmRespDto<Page<EnrollRespDto>> getEnrollList(@PathVariable long clubId, Pageable pageable) {

        Page<EnrollRespDto> enrollList = enrollService.getEnrollList(pageable, clubId);
        return new CmRespDto<>(1, "가입 리스트 조회", enrollList);
    }
}
