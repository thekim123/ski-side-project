package com.ski.backend.carpool.service;

import com.ski.backend.carpool.entity.SubmitState;
import com.ski.backend.config.auth.PrincipalDetails;
import com.ski.backend.carpool.entity.Carpool;
import com.ski.backend.carpool.entity.Submit;
import com.ski.backend.user.entity.User;
import com.ski.backend.user.entity.Whisper;
import com.ski.backend.handler.ex.CustomApiException;
import com.ski.backend.carpool.repository.CarpoolRepository;
import com.ski.backend.carpool.repository.SubmitRepository;
import com.ski.backend.repository.WhisperRepository;
import com.ski.backend.carpool.dto.AdmitDto;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
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

    /**
     * 카풀 신청 제출 서비스 로직
     *
     * @param authentication 로그인 정보
     * @param toCarpoolId    카풀 게시글 id
     * @apiNote Modifying 쿼리를 사용했던 것을 바꿈.
     * 성능상의 핸디캡이 분명 있지만 아직 성능상의 문제보다는
     * 객체지향을 좀 더 생각하는게 좋을거 같아서 바꿈
     * @since 2023.04.23 수정
     */
    @Transactional
    public void submit(Authentication authentication, long toCarpoolId) {
        Carpool carpool = carpoolRepository.findById(toCarpoolId).orElseThrow(() -> {
            throw new EntityNotFoundException("카풀 게시글이 존재하지 않습니다.");
        });
        User loginUser = ((PrincipalDetails) authentication.getPrincipal()).getUser();

        Submit submit = Submit.builder()
                .toCarpool(carpool)
                .fromUser(loginUser)
                .state(SubmitState.대기)
                .build();

        submitRepository.save(submit);
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
        Carpool carpoolEntity = carpoolRepository.findById(carpoolId).orElseThrow(() -> new EntityNotFoundException("카풀 게시글을 찾을 수 없습니다."));

        // 잘 되는지 테스트해야 돼요.
        carpoolEntity.increaseCurPassenger();
        Submit submitEntity = submitRepository.findByFromUserIdAndToCarpoolId(dto.getAdmitUserId(), dto.getToCarpoolId()).orElseThrow(() -> {
            throw new EntityNotFoundException("해당 카풀 신청 데이터를 찾을 수 없습니다.");
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
            throw new EntityNotFoundException("해당 카풀 신청 데이터를 찾을 수 없습니다.");
        });
        submitEntity.setState("거절");
    }

    @Transactional(readOnly = true)
    public List<Submit> getMySubmit(Authentication authentication) {
        Long userId = ((PrincipalDetails) authentication.getPrincipal()).getUser().getId();
        return submitRepository.findByFromUserId(userId);
    }

}
