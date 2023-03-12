package com.ski.backend.service;

import com.ski.backend.config.auth.PrincipalDetails;
import com.ski.backend.domain.carpool.Carpool;
import com.ski.backend.domain.carpool.Submit;
import com.ski.backend.domain.user.ChatRoom;
import com.ski.backend.domain.user.User;
import com.ski.backend.domain.user.Whisper;
import com.ski.backend.handler.ex.CustomApiException;
import com.ski.backend.repository.CarpoolRepository;
import com.ski.backend.repository.ChatRoomRepository;
import com.ski.backend.repository.SubmitRepository;
import com.ski.backend.repository.WhisperRepository;
import com.ski.backend.web.dto.AdmitDto;
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

    // 테스트해야되는 메서드
    @Transactional
    public void admit(AdmitDto dto, Authentication authentication) {

        long carpoolId = dto.getToCarpoolId();
        Carpool carpoolEntity = carpoolRepository.findById(carpoolId).orElseThrow(() -> new IllegalArgumentException("카풀 게시글을 찾을 수 없습니다."));

        // 잘 되는지 테스트해야 돼요.
        carpoolEntity.increaseCurPassenger();
        Submit submitEntity = submitRepository.findByFromUserIdAndToCarpoolId(dto.getAdmitUserId(), dto.getToCarpoolId());

        User principal = getPrincipal(authentication);

        Whisper whisperEntity = Whisper.builder()
                .principal(principal)
                .toUsername(carpoolEntity.getUser().getNickname())
                .build();
        whisperRepository.save(whisperEntity);

        Whisper writerWhisperEntity = Whisper.builder()
                .principal(carpoolEntity.getUser())
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
        Submit submitEntity = submitRepository.findByFromUserIdAndToCarpoolId(dto.getAdmitUserId(), dto.getToCarpoolId());
        submitEntity.setState("거절");
    }

    @Transactional(readOnly = true)
    public List<Submit> getMySubmit(Authentication authentication) {
        System.out.println(getPrincipalId(authentication));
        return submitRepository.findByFromUserId(getPrincipalId(authentication));
    }

    public User getPrincipal(Authentication authentication) {
        PrincipalDetails principalDetails = (PrincipalDetails) authentication.getPrincipal();
        return principalDetails.getUser();
    }

    public Long getPrincipalId(Authentication authentication) {
        return getPrincipal(authentication).getId();
    }
}
