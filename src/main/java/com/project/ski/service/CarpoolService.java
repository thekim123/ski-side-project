package com.project.ski.service;

import com.project.ski.config.auth.PrincipalDetails;
import com.project.ski.domain.carpool.Carpool;
import com.project.ski.domain.carpool.Negotiate;
import com.project.ski.domain.user.User;
import com.project.ski.repository.CarpoolRepository;
import com.project.ski.repository.NegotiateRepository;
import com.project.ski.web.dto.CarpoolRequestDto;
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
        carpoolRepository.save(carpoolEntity);

        negotiateEntity.setCarpool(carpoolEntity);
    }

    @Transactional
    public void delete(long carpoolId) {
        Carpool carpoolEntity = carpoolRepository.findById(carpoolId).orElseThrow(() -> {
            return new IllegalArgumentException("카풀 글 삭제 실패 : 게시글의 ID를 찾을 수 없습니다.");
        });
        carpoolRepository.delete(carpoolEntity);
    }

    @Transactional
    public void update(CarpoolRequestDto dto, long carpoolId) {
        dto.setId(carpoolId);
        Carpool carpoolEntity = carpoolRepository.findById(carpoolId).orElseThrow(() -> {
            return new IllegalArgumentException("카풀 글 수정 실패 : 게시글의 ID를 찾을 수 없습니다.");
        });
        carpoolEntity.setMemo(dto.getMemo());
        carpoolEntity.setDepartTime(dto.getDepartTime());
        carpoolEntity.setPassenger(dto.getPassenger());
        carpoolEntity.setDestination(dto.getDestination());
        carpoolEntity.setDeparture(dto.getDeparture());
    }

    @Transactional(readOnly = true)
    public Page<Carpool> getAll(Pageable pageable) {
        return carpoolRepository.findAll(pageable);
    }

    @Transactional(readOnly = true)
    public Carpool detail(long carpoolId) {
        Carpool carpoolEntity = carpoolRepository.findById(carpoolId).orElseThrow(() -> {
            return new IllegalArgumentException("해당 카풀 글이 존재하지 않습니다.");
        });
        return carpoolEntity;
    }

    public User getPrincipal(Authentication authentication) {
        PrincipalDetails principalDetails = (PrincipalDetails) authentication.getPrincipal();
        User user = principalDetails.getUser();
        return user;
    }
}
