package com.ski.backend.service;

import com.ski.backend.config.auth.PrincipalDetails;
import com.ski.backend.domain.carpool.Carpool;
import com.ski.backend.domain.carpool.Negotiate;
import com.ski.backend.domain.user.User;
import com.ski.backend.repository.CarpoolRepository;
import com.ski.backend.repository.NegotiateRepository;
import com.ski.backend.web.dto.CarpoolRequestDto;
import com.ski.backend.web.dto.NegotiateDto;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeMap;
import org.modelmapper.config.Configuration;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Service
@RequiredArgsConstructor
public class CarpoolService {

    private final CarpoolRepository carpoolRepository;
    private final NegotiateRepository negotiateRepository;

    // 엄..... 이거 맞냐?
    @PersistenceContext
    private EntityManager entityManager;

    @Transactional
    public void write(CarpoolRequestDto.Save dto, Authentication authentication) {
        User user = getPrincipal(authentication);

        Negotiate negotiate = new Negotiate();
        negotiate.mapEntityWhenUpdate(dto.getNegotiateDto());

        Carpool carpoolEntity = new Carpool();
        carpoolEntity.mapEntityWhenUpdate(dto);
        carpoolEntity.withUserAndNegotiate(user, negotiate);
        // 이거 바꿔야해
        carpoolEntity.setCurPassengerWithDefaultValue();

        negotiate.withCarpool(carpoolEntity);
        carpoolRepository.save(carpoolEntity);
        negotiateRepository.save(negotiate);

    }

    @Transactional
    public void delete(long carpoolId) {
        Carpool carpoolEntity = carpoolRepository.findById(carpoolId).orElseThrow(() -> new IllegalArgumentException("카풀 글 삭제 실패 : 게시글의 ID를 찾을 수 없습니다."));
        carpoolRepository.delete(carpoolEntity);
    }

    @Transactional
    public void update(CarpoolRequestDto.Save dto) {
        Carpool carpoolEntity = entityManager.find(Carpool.class, dto.getId());
        carpoolEntity.mapEntityWhenUpdate(dto);

        Negotiate negotiateEntity = carpoolEntity.getNegotiate();
        negotiateEntity.mapEntityWhenUpdate(dto.getNegotiateDto());

        entityManager.merge(carpoolEntity);
        entityManager.flush();
    }

    @Transactional(readOnly = true)
    public Page<Carpool> getAll(Pageable pageable) {
        return carpoolRepository.findAll(pageable);
    }

    @Transactional(readOnly = true)
    public Carpool detail(long carpoolId) {
        return carpoolRepository.findById(carpoolId).orElseThrow(() -> new IllegalArgumentException("해당 카풀 글이 존재하지 않습니다."));
    }


    /**
     * 아래는 추출한 메서드
     */

    public User getPrincipal(Authentication authentication) {
        PrincipalDetails principalDetails = (PrincipalDetails) authentication.getPrincipal();
        return principalDetails.getUser();
    }
}
