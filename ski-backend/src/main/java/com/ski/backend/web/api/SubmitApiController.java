package com.ski.backend.web.api;

import com.ski.backend.config.auth.PrincipalDetails;
import com.ski.backend.domain.carpool.Submit;
import com.ski.backend.service.SubmitService;
import com.ski.backend.web.dto.AdmitDto;
import com.ski.backend.web.dto.CmRespDto;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/submit/")
public class SubmitApiController {

    private final SubmitService submitService;

    @PostMapping("{toCarpoolId}")
    public CmRespDto<?> submit(Authentication authentication, @PathVariable long toCarpoolId) {
        PrincipalDetails principalDetails = (PrincipalDetails) authentication.getPrincipal();
        long userId = principalDetails.getUser().getId();
        submitService.submit(userId, toCarpoolId);
        return new CmRespDto<>(1, "제출 성공", null);
    }

    @DeleteMapping("{toCarpoolId}")
    public CmRespDto<?> unSubmit(Authentication authentication, @PathVariable long toCarpoolId) {
        PrincipalDetails principalDetails = (PrincipalDetails) authentication.getPrincipal();
        long userId = principalDetails.getUser().getId();
        submitService.unSubmit(userId, toCarpoolId);
        return new CmRespDto<>(1, "삭제 성공", null);
    }

    @GetMapping("{toCarpoolId}")
    public CmRespDto<?> getSubmit(@PathVariable long toCarpoolId, Authentication authentication) {
        PrincipalDetails principalDetails = (PrincipalDetails) authentication.getPrincipal();
        long userId = principalDetails.getUser().getId();
        List<Submit> submitList = submitService.getSubmit(toCarpoolId);
        return new CmRespDto<>(1, "카풀 제출 리스트 가져오기 성공", submitList);
    }

    @PutMapping("admit")
    public CmRespDto<?> admit(@RequestBody AdmitDto dto) {
        submitService.admit(dto);
        return new CmRespDto<>(1, "승인 성공", null);
    }

    @PutMapping("refuse")
    public CmRespDto<?> unAdmit(@RequestBody AdmitDto dto) {
        submitService.refuseAdmit(dto);
        return new CmRespDto<>(1, "거부 성공", null);
    }
}
