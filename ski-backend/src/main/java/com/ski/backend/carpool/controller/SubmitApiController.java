package com.ski.backend.carpool.controller;

import com.ski.backend.carpool.entity.Submit;
import com.ski.backend.carpool.service.SubmitService;
import com.ski.backend.carpool.dto.AdmitDto;
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

    @GetMapping("/getMySubmit")
    public CmRespDto<?> getMySubmit(Authentication authentication) {
        List<Submit> submitList = submitService.getMySubmit(authentication);
        return new CmRespDto<>(1, "내가 신청한 카풀 조회 성공", submitList);
    }

    @PostMapping("{toCarpoolId}")
    public CmRespDto<?> submit(Authentication authentication, @PathVariable long toCarpoolId) {
        submitService.submit(authentication, toCarpoolId);
        return new CmRespDto<>(1, "제출 성공", null);
    }

    @DeleteMapping("{toCarpoolId}")
    public CmRespDto<?> unSubmit(Authentication authentication, @PathVariable long toCarpoolId) {
        submitService.unSubmit(authentication, toCarpoolId);
        return new CmRespDto<>(1, "삭제 성공", null);
    }

    @GetMapping("{toCarpoolId}")
    public CmRespDto<?> getSubmit(@PathVariable long toCarpoolId) {
        List<Submit> submitList = submitService.getSubmit(toCarpoolId);
        return new CmRespDto<>(1, "카풀 제출 리스트 가져오기 성공", submitList);
    }

    @PutMapping("admit")
    public CmRespDto<?> admit(@RequestBody AdmitDto dto, Authentication authentication) {
        submitService.admit(dto, authentication);
        return new CmRespDto<>(1, "승인 성공", null);
    }

    @PutMapping("refuse")
    public CmRespDto<?> unAdmit(@RequestBody AdmitDto dto) {
        submitService.refuseAdmit(dto);
        return new CmRespDto<>(1, "거부 성공", null);
    }
}
