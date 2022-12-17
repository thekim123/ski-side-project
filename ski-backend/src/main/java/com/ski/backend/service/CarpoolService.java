package com.ski.backend.service;

import com.ski.backend.config.auth.PrincipalDetails;
import com.ski.backend.domain.carpool.Carpool;
import com.ski.backend.domain.carpool.Negotiate;
import com.ski.backend.domain.user.User;
import com.ski.backend.repository.CarpoolRepository;
import com.ski.backend.repository.NegotiateRepository;
import com.ski.backend.web.dto.CarpoolRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CarpoolService {

    private final CarpoolRepository carpoolRepository;
    private final NegotiateRepository negotiateRepository;

    @Transactional
    public void write(CarpoolRequestDto dto, Authentication authentication) {
        User user = getPrincipal(authentication);

        Negotiate negotiate = dto.getNegotiate();
        Negotiate negotiateEntity = negotiateRepository.save(negotiate);

        Carpool carpoolEntity = dto.toEntity();

        carpoolEntity.setNegotiate(negotiateEntity);
        carpoolEntity.setUser(user);
        carpoolEntity.setCurPassenger(0);
        carpoolEntity.setSmoker(dto.isSmoker());
        carpoolRepository.save(carpoolEntity);

        negotiateEntity.setCarpool(carpoolEntity);
    }

    @Transactional
    public void delete(long carpoolId) {
        Carpool carpoolEntity = carpoolRepository.findById(carpoolId).orElseThrow(() -> new IllegalArgumentException("카풀 글 삭제 실패 : 게시글의 ID를 찾을 수 없습니다."));
        carpoolRepository.delete(carpoolEntity);
    }

    @Transactional
    public void update(CarpoolRequestDto dto, long carpoolId) {
        Carpool carpoolEntity = carpoolRepository.findById(carpoolId).orElseThrow(() -> new IllegalArgumentException("카풀 글 수정 실패 : 게시글의 ID를 찾을 수 없습니다."));
        Long negotiateId = carpoolEntity.getNegotiate().getId();
        Negotiate negotiate = negotiateRepository.findById(negotiateId).orElseThrow(()-> new IllegalArgumentException("협상 아이디를 찾을 수 없습니다."));

        negotiate.setDestination(dto.getNegotiate().isDestination());
        negotiate.setDeparture(dto.getNegotiate().isDeparture());
        negotiate.setDepartTime(dto.getNegotiate().isDepartTime());
        negotiate.setBoardingPlace(dto.getNegotiate().isBoardingPlace());
        carpoolEntity.setNegotiate(negotiate);

        carpoolEntity.setMemo(dto.getMemo());
        carpoolEntity.setDepartTime(dto.getDepartTime());
        carpoolEntity.setPassenger(dto.getPassenger());
        carpoolEntity.setDestination(dto.getDestination());
        carpoolEntity.setDeparture(dto.getDeparture());
        carpoolEntity.setSmoker(dto.isSmoker());
        carpoolEntity.setBoarding(dto.getBoarding());
    }

    @Transactional(readOnly = true)
    public Page<Carpool> getAll(Pageable pageable) {
        return carpoolRepository.findAll(pageable);
    }

    @Transactional(readOnly = true)
    public Carpool detail(long carpoolId) {
        return carpoolRepository.findById(carpoolId).orElseThrow(() -> new IllegalArgumentException("해당 카풀 글이 존재하지 않습니다."));
    }

    public User getPrincipal(Authentication authentication) {
        PrincipalDetails principalDetails = (PrincipalDetails) authentication.getPrincipal();
        return principalDetails.getUser();
    }
}
