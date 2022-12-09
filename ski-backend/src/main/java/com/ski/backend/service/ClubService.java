package com.ski.backend.service;

import com.ski.backend.domain.club.*;
import com.ski.backend.domain.resort.Resort;
import com.ski.backend.domain.user.User;
import com.ski.backend.web.dto.ClubRequestDto;
import com.ski.backend.web.dto.ClubResponseDto;
import com.ski.backend.web.dto.ClubUserRespDto;
import com.ski.backend.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;


@Service
@RequiredArgsConstructor
public class ClubService {

    private final ClubRepository clubRepository;
    private final ClubUserRepository clubUserRepository;
    private final ClubBoardRepository clubBoardRepository;
    private final UserRepository userRepository;
    private final ResortRepository resortRepository;
    private final ReplyRepository replyRepository;
    private final EnrollRepository enrollRepository;

    // 동호회 첫 화면 목록조회
    @Transactional(readOnly = true)
    public Page<ClubResponseDto> clubList(Pageable pageable) {
        Page<Club> clubPage =clubRepository.findAll(pageable);
        Page<ClubResponseDto> dto = clubPage.map(e -> new ClubResponseDto(e.getId(),e.getMemberCnt(), e.getClubNm(), e.getResort().getId(),e.getOpenYn()));
        return dto;

    }
    // 동호회별 유저목록 조회
    @Transactional(readOnly = true)
    public Page<ClubUserRespDto> getUserClubList(Pageable pageable, Long clubId) {
        return clubUserRepository.findByUser_Id(pageable,clubId).map(ClubUserRespDto::new);

    }


    // 동호회 생성
    @Transactional
    public void create(ClubRequestDto dto, User user) {

        User findUser = userRepository.findById(user.getId()).orElseThrow(() -> {
            return new IllegalArgumentException("실패");
        });
        Resort resort = resortRepository.findById(dto.getResortId()).orElseThrow(() -> {
            return new IllegalArgumentException("리조트명 찾기 실패");
        });

        Club club = dto.toEntity(user, resort);
        clubRepository.save(club);
        ClubUser clubUser = new ClubUser(club, findUser);
        findUser.getClubUsers().add(clubUser);
        clubUser.setRole("관리자");
    }

    // 동호회 삭제
    @Transactional
    public void delete(long clubId) {
        Club club = clubRepository.findById(clubId).orElseThrow(()-> new IllegalArgumentException("동호회 삭제 실패"));
        List<ClubUser> clubUser = clubUserRepository.findByClub(club);
        if(clubUser.size() == 0){
            throw new IllegalArgumentException("오류");
        }

        Optional<ClubBoard> byClub = clubBoardRepository.findByClub(club);
        boolean present = byClub.isPresent();
        if (present) {
            List<Reply> replyList = replyRepository.findByClubBoard(byClub.get());
            Optional<ClubUser> cu = clubUserRepository.findByClubBoard(byClub.get());
            if(byClub.get().getClub().getId() == club.getId()){
                clubBoardRepository.delete(byClub.get());
                replyRepository.deleteAll(replyList);
                clubUserRepository.delete(cu.get());
            }
        }
        List<Enroll> enrollList = enrollRepository.findByClub(club);
        if(enrollList.size() != 0){

        enrollRepository.deleteAll(enrollList);
        }
        clubUserRepository.deleteAll(clubUser);
        clubRepository.delete(club);
    }


    // 동호회 수정
    @Transactional
    public void update(long clubId, ClubRequestDto dto) {
        Club clubs = clubRepository.findById(clubId).orElseThrow(()-> new IllegalArgumentException("동호회 수정 실패"));

        Resort resort = resortRepository.findById(dto.getResortId()).orElseThrow(() -> new IllegalArgumentException("동호회 리조트 찾기  실패"));
        clubs.update(dto,resort);

    }

    // 동호회 탈퇴
    public void deleteMember(long userId, long clubId) {
        ClubUser clubUser = clubUserRepository.findByUserIdAndClubId(userId,clubId).orElseThrow(()->{
            return new IllegalArgumentException("동호회 탈퇴 실패" + userId);
        });

        clubUserRepository.delete(clubUser);

    }

    // 동호회 상세페이지
    @Transactional
    public Optional<ClubResponseDto> clubDetail(Long clubId) {
        Club dto = clubRepository.findById(clubId).orElseThrow(()->{
            return new IllegalArgumentException("글 상세보기 실패: 해당게시글을 찾을 수 없습니다.");
        });
        return clubRepository.findById(clubId).map(ClubResponseDto::new);
    }

}
