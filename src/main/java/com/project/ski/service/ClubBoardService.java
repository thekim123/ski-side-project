package com.project.ski.service;


import com.project.ski.domain.club.ClubBoard;
import com.project.ski.domain.club.ClubUser;
import com.project.ski.domain.user.User;
import com.project.ski.repository.ClubBoardRepository;
import com.project.ski.repository.ClubUserRepository;
import com.project.ski.web.dto.ClubBoardDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ClubBoardService {

    private final ClubBoardRepository clubBoardRepository;
    private final ClubUserRepository clubUserRepository;

    /**
     * 동호회 게시판
     * 상세 조회 -{boardId}
     */
    @Transactional(readOnly = true)
    public ClubBoardDto getClubBoard(long clubBoardId) {
        return clubBoardRepository.findById(clubBoardId);
    }


    /**
     * 동호회 게시판 생성
     * 해당 동호회에 가입한 유저만이 게시판에 글을 쓸 수 있음
     *
     */
    @Transactional(readOnly = true)
    public ClubBoardDto createClubBoard(ClubBoardDto dto, User user, long clubId ) {
        // clubUser의 clubId 와 같은지 체크
        ClubUser findUser = clubUserRepository.findByUserIdAndClubId(user.getId(),clubId).orElseThrow(()->{
            return new IllegalArgumentException("실패");
        });
        ClubBoard clubBoard = dto.toEntity();

        clubBoardRepository.save(clubBoard);


        return dto;
    }



}
