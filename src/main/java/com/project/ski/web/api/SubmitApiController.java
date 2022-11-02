package com.project.ski.web.api;

import com.project.ski.config.auth.PrincipalDetails;
import com.project.ski.service.SubmitService;
import com.project.ski.web.dto.CmRespDto;
import com.project.ski.web.dto.SubmitDto;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class SubmitApiController {

    private final SubmitService submitService;

    @PostMapping("/api/submit/{toCarpoolId}")
    public CmRespDto<?> submit(Authentication authentication, @PathVariable long toCarpoolId) {
        PrincipalDetails principalDetails = (PrincipalDetails) authentication.getPrincipal();
        long userId = principalDetails.getUser().getId();
        submitService.submit(userId, toCarpoolId);
        return new CmRespDto<>(1, "제출 성공", null);
    }

    @DeleteMapping("/api/submit/{toCarpoolId}")
    public CmRespDto<?> unSubmit(Authentication authentication, @PathVariable long toCarpoolId) {
        PrincipalDetails principalDetails = (PrincipalDetails) authentication.getPrincipal();
        long userId = principalDetails.getUser().getId();
        submitService.unSubmit(userId, toCarpoolId);
        return new CmRespDto<>(1, "삭제 성공", null);
    }

    @GetMapping("/api/submit/{toCarpoolId}")
    public CmRespDto<?> getSubmit(@PathVariable long toCarpoolId, Authentication authentication) {
        PrincipalDetails principalDetails = (PrincipalDetails) authentication.getPrincipal();
        long userId = principalDetails.getUser().getId();

        return new CmRespDto<>(1, "카풀 제출 리스트 가져오기 성공", submitService.getSubmit(toCarpoolId));
    }

}
