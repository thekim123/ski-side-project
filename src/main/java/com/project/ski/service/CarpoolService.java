package com.project.ski.service;

import com.project.ski.domain.carpool.Carpool;
import com.project.ski.domain.user.User;
import com.project.ski.repository.CarpoolRepository;
import com.project.ski.web.dto.CarpoolRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CarpoolService {

    private final CarpoolRepository carpoolRepository;

    @Transactional
    public void write(CarpoolRequestDto dto, User user) {
        Carpool carpoolEntity = dto.toEntity();
        carpoolEntity.setUser(user);
        carpoolRepository.save(carpoolEntity);
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
        carpoolEntity.setCost(dto.getCost());
        carpoolEntity.setBoarding(dto.getBoarding());
        carpoolEntity.setDepartTime(dto.getDepartTime());
        carpoolEntity.setSmoke(dto.getSmoke());
        carpoolEntity.setPassenger(dto.getPassenger());
        carpoolEntity.setPhoneNumber(dto.getPhoneNumber());
        carpoolEntity.setDestination(dto.getDestination());
        carpoolEntity.setSmoke(dto.getSmoke());
        carpoolEntity.setDeparture(dto.getDeparture());
    }

    @Transactional(readOnly = true)
    public Page<Carpool> getAll(Pageable pageable) {
        return carpoolRepository.findAll(pageable);
    }
}
