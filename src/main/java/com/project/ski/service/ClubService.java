package com.project.ski.service;

import com.project.ski.domain.club.Club;
import com.project.ski.domain.user.User;
import com.project.ski.repository.ClubRepository;
import com.project.ski.web.dto.ClubRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ClubService {

    private final ClubRepository clubRepository;


    // 목록 조회
    @Transactional(readOnly = true)
    public Page<Club> clubList(Pageable pageable, long id) {
        return clubRepository.findAll(pageable);

    }

    // 동호회 생성
    @Transactional
    public void create(ClubRequestDto dto,User user) {

        Club clubs = dto.toEntity(user);
        clubRepository.save(clubs);
    }

    // 동호회 삭제
    @Transactional
    public void delete(long clubId) {
        Club club = clubRepository.findById(clubId).orElseThrow(()->{
            return new IllegalArgumentException("동호회 삭제 실패");
        });
        clubRepository.delete(club);

    }

    // 동호회 수정
    @Transactional
    public void update(long clubId, ClubRequestDto dto) {
        Club clubs = clubRepository.findById(clubId).orElseThrow(()->{
            return new IllegalArgumentException("동호회 수정 실패");
        });
        clubs.update(dto);

    }

}
