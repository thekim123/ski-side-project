package com.ski.backend.service;

import com.ski.backend.config.auth.PrincipalDetails;
import com.ski.backend.domain.carpool.Carpool;
import com.ski.backend.domain.carpool.Submit;
import com.ski.backend.handler.ex.CustomApiException;
import com.ski.backend.repository.CarpoolRepository;
import com.ski.backend.repository.SubmitRepository;
import com.ski.backend.web.dto.AdmitDto;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SubmitService {
    private final SubmitRepository submitRepository;
    private final CarpoolRepository carpoolRepository;

    @Transactional(readOnly = true)
    public List<Submit> getSubmit(long toCarpoolId) {
        return submitRepository.findByToCarpoolId(toCarpoolId);
    }

    @Transactional
    public void submit(Authentication authentication, long toCarpoolId) {
        Long fromUserId = getPrincipalId(authentication);

        try {
            submitRepository.mSubmit(fromUserId, toCarpoolId);
        } catch (Exception e) {
            throw new CustomApiException("이미 제출을 하셨습니다.");
        }
    }

    @Transactional
    public void unSubmit(Authentication authentication, long toCarpoolId) {
        Long fromUserId = getPrincipalId(authentication);
        submitRepository.mUnSubmit(fromUserId, toCarpoolId);
    }

    @Transactional
    public void admit(AdmitDto dto) {
        long carpoolId = dto.getToCarpoolId();
        Carpool carpoolEntity = carpoolRepository.findById(carpoolId).orElseThrow(() -> new IllegalArgumentException("카풀 게시글을 찾을 수 없습니다."));
        carpoolEntity.setCurPassenger(carpoolEntity.getCurPassenger() + 1);
        Submit submitEntity = submitRepository.findByFromUserIdAndToCarpoolId(dto.getAdmitUserId(), dto.getToCarpoolId());
        submitEntity.setState("승인");
    }

    @Transactional
    public void refuseAdmit(AdmitDto dto) {
        Submit submitEntity = submitRepository.findByFromUserIdAndToCarpoolId(dto.getAdmitUserId(), dto.getToCarpoolId());
        submitEntity.setState("거절");
    }

    @Transactional(readOnly = true)
    public List<Submit> getMySubmit(Authentication authentication) {
        System.out.println(getPrincipalId(authentication));
        return submitRepository.findByFromUserId(getPrincipalId(authentication));
    }

    public Long getPrincipalId(Authentication authentication) {
        PrincipalDetails principalDetails = (PrincipalDetails) authentication.getPrincipal();
        return principalDetails.getUser().getId();
    }
}
