package com.project.ski.service;

import com.project.ski.domain.club.Club;
import com.project.ski.domain.club.ClubUser;
import com.project.ski.domain.user.User;
import com.project.ski.repository.ClubRepository;
import com.project.ski.repository.ClubUserRepository;
import com.project.ski.repository.UserRepository;
import com.project.ski.web.dto.ClubRequestDto;
import com.project.ski.web.dto.ClubResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@RequiredArgsConstructor
public class ClubService {

    private final ClubRepository clubRepository;
    private final ClubUserRepository clubUserRepository;

    private final UserRepository userRepository;

    // 동호회 첫 화면 목록조회
    @Transactional(readOnly = true)
    public Page<ClubResponseDto> clubList(Pageable pageable) {
        Page<Club> clubPage =clubRepository.findAll(pageable);
        Page<ClubResponseDto> dto = clubPage.map(e -> new ClubResponseDto(e.getMemberCnt(), e.getClubNm(), e.getResort().getId()));
        return dto;

    }
    // user별 동호회목록 조회
    @Transactional(readOnly = true)
    public Page<Club> getUserClubList(Pageable pageable, User user, String tempFlag) {
        return clubRepository.findByTempFlagAndUser_Id(pageable, user.getId(), tempFlag);

    }


    // 동호회 생성
    @Transactional
    public void create(ClubRequestDto dto,User user) {
        User findUser = userRepository.findById(user.getId()).orElseThrow(()->{
            return new IllegalArgumentException("실패");
        });
        Club club = dto.toEntity(user);
        clubRepository.save(club);
        ClubUser clubUser = new ClubUser(club, findUser);
        findUser.getClubUsers().add(clubUser);

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

    // 동호회 탈퇴
    public void deleteMember(long userId, long clubId) {
        ClubUser clubUser = clubUserRepository.findByUserIdAndClubId(userId,clubId).orElseThrow(()->{
            return new IllegalArgumentException("동호회 탈퇴 실패");
        });

        clubUserRepository.delete(clubUser);
    }



}
