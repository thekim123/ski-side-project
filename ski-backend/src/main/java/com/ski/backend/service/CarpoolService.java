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
        ModelMapper mapper = new ModelMapper();
        User user = getPrincipal(authentication);

        TypeMap<NegotiateDto, Negotiate> negotiateTypeMap = mapper.createTypeMap(NegotiateDto.class, Negotiate.class);
        typeMapWithNegotiate(negotiateTypeMap);
        Negotiate negotiate = mapper.map(dto.getNegotiateDto(), Negotiate.class);
        Negotiate negotiateEntity = negotiateRepository.save(negotiate);

        TypeMap<CarpoolRequestDto.Save, Carpool> carpoolTypeMap = mapper.createTypeMap(CarpoolRequestDto.Save.class, Carpool.class);
        typeMapWithCarpools(carpoolTypeMap);
        Carpool carpoolEntity = mapper.map(dto, Carpool.class);
        carpoolEntity.withUserAndNegotiate(user, negotiate);
        carpoolEntity.setCurPassengerWithDefaultValue();
        carpoolRepository.save(carpoolEntity);

    }

    @Transactional
    public void delete(long carpoolId) {
        Carpool carpoolEntity = carpoolRepository.findById(carpoolId).orElseThrow(() -> new IllegalArgumentException("카풀 글 삭제 실패 : 게시글의 ID를 찾을 수 없습니다."));
        carpoolRepository.delete(carpoolEntity);
    }

    @Transactional
    public void update(CarpoolRequestDto.Save dto) {
        Carpool carpoolEntity = entityManager.find(Carpool.class, dto.getId());
        entityManager.persist(carpoolEntity);
        Negotiate negotiateEntity = carpoolEntity.getNegotiate();

        ModelMapper mapper = new ModelMapper();
        TypeMap<NegotiateDto, Negotiate> negotiateTypeMap = mapper.createTypeMap(NegotiateDto.class, Negotiate.class);
        typeMapWithNegotiate(negotiateTypeMap);
        mapper.map(dto.getNegotiateDto(), negotiateEntity);

        ModelMapper mapper1 = new ModelMapper();
        TypeMap<CarpoolRequestDto.Save, Carpool> carpoolTypeMap = mapper1.createTypeMap(CarpoolRequestDto.Save.class, Carpool.class);
        typeMapWithCarpools(carpoolTypeMap);
        mapper1.map(dto, carpoolEntity);
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

    public void typeMapWithNegotiate(TypeMap<NegotiateDto, Negotiate> typeMap) {
        typeMap.setProvider(request -> {
            NegotiateDto source = (NegotiateDto) request.getSource();
            return Negotiate.builder()
                    .boardingPlace(source.isBoardingPlace())
                    .destination(source.isDestination())
                    .departTime(source.isDepartTime())
                    .departure(source.isDeparture())
                    .build();
        });
    }

    public void typeMapWithCarpools(TypeMap<CarpoolRequestDto.Save, Carpool> typeMap) {
        typeMap.setProvider(request -> {
            CarpoolRequestDto.Save source = (CarpoolRequestDto.Save) request.getSource();
            return Carpool.builder()
                    .id(source.getId())
                    .departure(source.getDeparture())
                    .destination(source.getDestination())
                    .boarding(source.getBoarding())
                    .isSmoker(source.isSmoker())
                    .passenger(source.getPassenger())
                    .memo(source.getMemo())
                    .request(source.getRequest())
                    .negotiate(typeMapWithNegotiate().map(source.getNegotiateDto()))
                    .departTime(LocalDateTime.parse(source.getDepartTime(), DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")))
                    .build();
        });
    }

    public TypeMap<NegotiateDto, Negotiate> typeMapWithNegotiate() {
        ModelMapper mapper = new ModelMapper();
        TypeMap<NegotiateDto, Negotiate> typeMap = mapper.createTypeMap(NegotiateDto.class, Negotiate.class);
        typeMap.setProvider(request -> {
            NegotiateDto source = (NegotiateDto) request.getSource();
            return Negotiate.builder()
                    .boardingPlace(source.isBoardingPlace())
                    .destination(source.isDestination())
                    .departTime(source.isDepartTime())
                    .departure(source.isDeparture())
                    .build();
        });
        return typeMap;
    }


    public User getPrincipal(Authentication authentication) {
        PrincipalDetails principalDetails = (PrincipalDetails) authentication.getPrincipal();
        return principalDetails.getUser();
    }
}
