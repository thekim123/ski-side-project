package com.ski.backend.carpool.service;

import com.ski.backend.carpool.entity.SubmitState;
import com.ski.backend.config.AuditorProvider;
import com.ski.backend.config.auth.PrincipalDetails;
import com.ski.backend.carpool.entity.Carpool;
import com.ski.backend.carpool.entity.Submit;
import com.ski.backend.user.entity.User;
import com.ski.backend.carpool.repository.CarpoolRepository;
import com.ski.backend.carpool.repository.SubmitRepository;
import com.ski.backend.carpool.dto.AdmitDto;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class SubmitService {
    private final SubmitRepository submitRepository;
    private final CarpoolRepository carpoolRepository;

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

    /**
     * 카풀 신청 취소 서비스 로직
     *
     * @param authentication 로그인 정보
     * @param toCarpoolId    신청하려는 카풀 게시글 id
     * @since last modified at 2023.04.24
     */
    @Transactional
    public void unSubmit(Authentication authentication, long toCarpoolId) {
        Long fromUserId = ((PrincipalDetails) authentication.getPrincipal()).getUser().getId();
        submitRepository.mUnSubmit(fromUserId, toCarpoolId);
    }

    /**
     * 카풀 승인 서비스 로직
     *
     * @param dto            승인 dto
     * @param authentication 로그인 정보
     * @apiNote 승인을 받으면 채팅창이 연결되도록 하였다.
     * 현재는 채팅서버에서 인증이 불가능하여 이곳의 db를 사용하고 있는 상황이다.
     * @since last modified at 2023.04.24
     */
    @Transactional
    public void admit(AdmitDto dto, Authentication authentication) {
        long carpoolId = dto.getToCarpoolId();
        Carpool carpoolEntity = carpoolRepository.findById(carpoolId).orElseThrow(() -> new EntityNotFoundException("카풀 게시글을 찾을 수 없습니다."));

        AuditorProvider ad = new AuditorProvider();

        if (isCarpoolWriter(carpoolEntity, ad)) {
            throw new AccessDeniedException("해당 게시글의 작성자가 아닙니다.");
        }

        carpoolEntity.increaseCurPassenger();
        Submit submitEntity = submitRepository.findByFromUserIdAndToCarpoolId(dto.getAdmitUserId(), dto.getToCarpoolId()).orElseThrow(() -> {
            throw new EntityNotFoundException("해당 카풀 신청 데이터를 찾을 수 없습니다.");
        });
        submitEntity.setState("승인");

    }

    /**
     * 카풀 게시글의 작성자인지 체크하는 메서드
     *
     * @param carpoolEntity 카풀 entity
     * @param ad            auditorProvider
     * @return 카풀 게시글의 작성자(true)인지 여부
     * @apiNote 단순한 메서드일텐데 왜 이렇게 길어지게 되었느냐면,
     * ad 에서 username 을 가져올 때 타입이 Optional 이고, Optional<String>과 String 을 비교하는 데에서 경고가 나왔다.
     * 그래서 .get()을 붙이니 isPresent() 없이 get()을 썻다고 경고가 재차 뜸.
     * 그래서 길어졌다.
     * @since 2023.04.24
     */
    private static boolean isCarpoolWriter(Carpool carpoolEntity, AuditorProvider ad) {
        return ad.getCurrentAuditor().isPresent() && !Objects.equals(ad.getCurrentAuditor().get(), carpoolEntity.getWriter().getUsername());
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
