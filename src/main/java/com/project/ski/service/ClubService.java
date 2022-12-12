package com.project.ski.service;

import com.project.ski.domain.club.*;
import com.project.ski.domain.resort.Resort;
import com.project.ski.domain.user.User;
import com.project.ski.repository.*;
import com.project.ski.web.dto.ClubRequestDto;
import com.project.ski.web.dto.ClubResponseDto;
import com.project.ski.web.dto.ClubUserRespDto;
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

        List<ClubBoard> byClub = clubBoardRepository.findByClub(club);
        if (byClub.size() > 0) {
            byClub.forEach(e-> {
                List<Reply> replyList = replyRepository.findByClubBoard(e);
                List<ClubUser> cu = clubUserRepository.findByClubBoard(e);

            for (int i = 0; i < byClub.size(); i++) {
                if(byClub.get(i).getClub().getId() == club.getId()){
                    clubBoardRepository.delete(byClub.get(i));
                    replyRepository.delete(replyList.get(i));
                    clubUserRepository.delete(cu.get(i));
                }

            }
            });

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
    public Optional<ClubResponseDto> clubDetail(Long clubId,User user) {
        Club dto = clubRepository.findById(clubId).orElseThrow(()->{
            return new IllegalArgumentException("글 상세보기 실패: 해당게시글을 찾을 수 없습니다.");
        });
        return clubRepository.findById(clubId).map(club -> new ClubResponseDto(club,user));
    }

}
