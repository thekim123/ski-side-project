package com.project.ski.service;


import com.project.ski.domain.Tayo.Tayo;
import com.project.ski.domain.resort.Resort;
import com.project.ski.domain.user.User;
import com.project.ski.repository.ResortRepository;
import com.project.ski.repository.TayoRepository;
import com.project.ski.web.dto.TayoRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TayoService {

    private final TayoRepository tayoRepository;

    private final ResortRepository resortRepository;

    // 목록조회
    @Transactional(readOnly = true)
    public Page<Tayo> tayoList(Pageable pageable, long id) {
        return tayoRepository.findAll(pageable);
    }
    // 스키장별 목록조회
    @Transactional
    public List<Tayo> getTayoByResortName(String resortName) {
        return tayoRepository.findByResortName(resortName);
    }

    @Transactional
    public void create(TayoRequestDto dto, User user) {
        Resort resort = resortRepository.findById(dto.getResortId()).orElseThrow(()->{
            return new IllegalArgumentException("리조트명 찾기 실패");
        });
        Tayo tayos = dto.toEntity(user,resort);
        tayoRepository.save(tayos);
    }

    @Transactional
    public void delete(long tayoId) {
        Tayo tayos = tayoRepository.findById(tayoId).orElseThrow(()->{
            return new IllegalArgumentException("같이 타요 삭제 실패");
        });
        tayoRepository.delete(tayos);
    }

    @Transactional
    public void update(long tayoId, TayoRequestDto dto) {
        Tayo tayos = tayoRepository.findById(tayoId).orElseThrow(()->{
            return new IllegalArgumentException("같이 타요 수정 실패");
        });

        tayos.update(dto);
    }
}
