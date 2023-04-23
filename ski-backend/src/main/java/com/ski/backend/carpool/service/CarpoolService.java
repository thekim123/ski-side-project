package com.ski.backend.carpool.service;

import com.ski.backend.config.auth.PrincipalDetails;
import com.ski.backend.carpool.entity.Carpool;
import com.ski.backend.carpool.entity.Negotiate;
import com.ski.backend.carpool.entity.RequestType;
import com.ski.backend.domain.user.User;
import com.ski.backend.carpool.repository.CarpoolRepository;
import com.ski.backend.handler.ex.CustomApiException;
import com.ski.backend.web.dto.carpool.CarpoolRequestDto;
import com.ski.backend.web.dto.carpool.CarpoolResponseDto;
import com.ski.backend.web.dto.carpool.NegotiateDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class CarpoolService {

    private final CarpoolRepository carpoolRepository;

    /**
     * 카풀 게시글 작성 서비스 로직
     *
     * @param dto            카풀 요청 dto
     * @param authentication 로그인 정보
     * @since 2023.04.22 수정
     */
    @Transactional
    public void write(CarpoolRequestDto dto, Authentication authentication) {
        User user = ((PrincipalDetails) authentication.getPrincipal()).getUser();

        Negotiate negotiate = Negotiate.builder()
                .nDeparture(dto.getNegotiateDto().isNDeparture())
                .nBoardingPlace(dto.getNegotiateDto().isNBoardingPlace())
                .nDestination(dto.getNegotiateDto().isNDestination())
                .nDepartTime(dto.getNegotiateDto().isNDepartTime())
                .build();

        Carpool carpoolEntity = Carpool.builder()
                .memo(dto.getMemo())
                .isSmoker(dto.isSmoker())
                .departure(dto.getDeparture())
                .boarding(dto.getBoarding())
                .destination(dto.getDestination())
                .departTime(LocalDateTime.parse(dto.getDepartTime()))
                .passenger(dto.getPassenger())
                .request(RequestType.valueOf(dto.getRequest()))
                .negotiate(negotiate)
                .build();

        carpoolEntity.withWriter(user);
        carpoolEntity.setCurPassengerWithDefaultValue();
        carpoolRepository.save(carpoolEntity);
    }

    @Transactional
    public void delete(long carpoolId) {
        Carpool carpoolEntity = carpoolRepository.findById(carpoolId).orElseThrow(() -> new EntityNotFoundException("카풀 글 삭제 실패 : 게시글의 ID를 찾을 수 없습니다."));
        carpoolRepository.delete(carpoolEntity);
    }


    /**
     * 카풀 게시글 수정 서비스 메서드
     *
     * @param dto 카풀 dto
     * @throws AccessDeniedException 정원이 다 찼는데 신청하면 던짐
     * @since 2023.04.22 마지막 수정
     */
    @Transactional
    public void update(CarpoolRequestDto dto) {
        Carpool carpoolEntity = carpoolRepository.findById(dto.getId()).orElseThrow(() -> {
            throw new EntityNotFoundException("카풀 게시글을 찾을 수 없습니다.");
        });

        isAtCapacity(carpoolEntity);
        carpoolEntity.update(dto);
        carpoolEntity.increaseCurPassenger();
    }

    /**
     * 카풀에 정원이 다 찼는지 판단
     *
     * @param carpoolEntity 카풀 entity
     * @throws AccessDeniedException 정원이 다 차면 던짐
     * @since 2023.04.22 추가
     */
    public void isAtCapacity(Carpool carpoolEntity) {
        if (carpoolEntity.getCurPassenger() == carpoolEntity.getPassenger()) {
            throw new CustomApiException("정원이 다 찼어요.");
        }
    }

    /**
     * 카풀 모든 게시글 조회
     *
     * @param pageable 페이지 정보
     * @return 카풀 게시글 페이징한 것
     * TODO: 이것도 entity 쌩으로 던지는데 수정할 것.
     */
    @Transactional(readOnly = true)
    public Page<Carpool> getAll(Pageable pageable) {
        return carpoolRepository.findAll(pageable);
    }

    /**
     * 카풀 게시글 상세보기
     *
     * @param carpoolId 카풀 entity id
     * @return 카풀 응답 dto
     * @since 2023.04.22 수정
     */
    @Transactional(readOnly = true)
    public CarpoolResponseDto detail(long carpoolId) {
        Carpool carpool = carpoolRepository.findById(carpoolId).orElseThrow(() -> new EntityNotFoundException("해당 카풀 글이 존재하지 않습니다."));
        return convertCarpoolToDto(carpool);
    }

    /**
     * Carpool -> Response Dto 로 변환해주는 메서드
     *
     * @param entity Carpool entity
     * @return CarpoolResponseDto
     * @apiNote 비즈니스 로직이 DTO 나 Entity 클래스 내부에 있으면 안된다 하여 여기에 작성함.
     * @since 2023.04.22 추가
     */
    public CarpoolResponseDto convertCarpoolToDto(Carpool entity) {
        NegotiateDto negotiateDto = NegotiateDto.builder()
                .nBoardingPlace(entity.getNegotiate().isNBoardingPlace())
                .nDepartTime(entity.getNegotiate().isNDepartTime())
                .nDestination(entity.getNegotiate().isNDestination())
                .nDeparture(entity.getNegotiate().isNDeparture())
                .build();

        return CarpoolResponseDto.builder()
                .id(entity.getId())
                .smoker(entity.isSmoker())
                .destination(entity.getDestination())
                .memo(entity.getMemo())
                .passenger(entity.getPassenger())
                .curPassenger(entity.getCurPassenger())
                .departure(entity.getDeparture())
                .departTime(entity.getDepartTime().toString())
                .negotiateDto(negotiateDto)
                .boarding(entity.getBoarding())
                .request(entity.getRequest().toString())
                .createdDate(entity.getCreatedDate())
                .lastModifiedDate(entity.getLastModifiedDate())
                .build();
    }

}
