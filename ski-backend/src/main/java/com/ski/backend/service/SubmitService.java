package com.ski.backend.service;

import com.ski.backend.config.auth.PrincipalDetails;
import com.ski.backend.domain.carpool.Carpool;
import com.ski.backend.domain.carpool.Submit;
import com.ski.backend.domain.user.User;
import com.ski.backend.domain.user.Whisper;
import com.ski.backend.handler.ex.CustomApiException;
import com.ski.backend.repository.CarpoolRepository;
import com.ski.backend.repository.SubmitRepository;
import com.ski.backend.repository.WhisperRepository;
import com.ski.backend.web.dto.carpool.AdmitDto;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SubmitService {
    private final SubmitRepository submitRepository;
    private final CarpoolRepository carpoolRepository;
    private final WhisperRepository whisperRepository;

    @Transactional(readOnly = true)
    public List<Submit> getSubmit(long toCarpoolId) {
        return submitRepository.findByToCarpoolId(toCarpoolId);
    }

    @Transactional
    public void submit(Authentication authentication, long toCarpoolId) {
        Long fromUserId = ((PrincipalDetails) authentication.getPrincipal()).getUser().getId();

        try {
            submitRepository.mSubmit(fromUserId, toCarpoolId);
        } catch (Exception e) {
            throw new CustomApiException("이미 제출을 하셨습니다.");
        }
    }

    @Transactional
    public void unSubmit(Authentication authentication, long toCarpoolId) {
        Long fromUserId = ((PrincipalDetails) authentication.getPrincipal()).getUser().getId();
        submitRepository.mUnSubmit(fromUserId, toCarpoolId);
    }

    // 테스트해야되는 메서드
    @Transactional
    public void admit(AdmitDto dto, Authentication authentication) {

        long carpoolId = dto.getToCarpoolId();
        Carpool carpoolEntity = carpoolRepository.findById(carpoolId).orElseThrow(() -> new IllegalArgumentException("카풀 게시글을 찾을 수 없습니다."));

        // 잘 되는지 테스트해야 돼요.
        carpoolEntity.increaseCurPassenger();
        Submit submitEntity = submitRepository.findByFromUserIdAndToCarpoolId(dto.getAdmitUserId(), dto.getToCarpoolId()).orElseThrow(() -> {
            throw new CustomApiException("해당 카풀 신청 데이터를 찾을 수 없습니다.");
        });

        User principal = ((PrincipalDetails) authentication.getPrincipal()).getUser();

        Whisper whisperEntity = Whisper.builder()
                .principal(principal)
                .toUsername(carpoolEntity.getWriter().getNickname())
                .build();

        Whisper writerWhisperEntity = Whisper.builder()
                .principal(carpoolEntity.getWriter())
                .toUsername(principal.getNickname())
                .build();

        List<Whisper> whispers = new ArrayList<>();
        whispers.add(whisperEntity);
        whispers.add(writerWhisperEntity);
        whisperRepository.saveAll(whispers);

        submitEntity.setState("승인");
    }

    @Transactional
    public void refuseAdmit(AdmitDto dto) {
        Submit submitEntity = submitRepository.findByFromUserIdAndToCarpoolId(dto.getAdmitUserId(), dto.getToCarpoolId()).orElseThrow(() -> {
            throw new CustomApiException("해당 카풀 신청 데이터를 찾을 수 없습니다.");
        });
        submitEntity.setState("거절");
    }

    @Transactional(readOnly = true)
    public List<Submit> getMySubmit(Authentication authentication) {
        Long userId = ((PrincipalDetails) authentication.getPrincipal()).getUser().getId();
        return submitRepository.findByFromUserId(userId);
    }

}
