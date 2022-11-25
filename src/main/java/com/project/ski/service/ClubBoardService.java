package com.project.ski.service;


import com.project.ski.domain.club.Club;
import com.project.ski.domain.club.ClubBoard;
import com.project.ski.domain.club.ClubUser;
import com.project.ski.domain.user.User;
import com.project.ski.repository.ClubBoardRepository;
import com.project.ski.repository.ClubRepository;
import com.project.ski.repository.ClubUserRepository;
import com.project.ski.repository.UserRepository;
import com.project.ski.web.dto.ClubBoardDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@RequiredArgsConstructor
public class ClubBoardService {

    private final ClubBoardRepository clubBoardRepository;
    private final UserRepository userRepository;
    private final ClubRepository clubRepository;
    /**
     * 동호회 게시판
     * 상세 조회 -{boardId}
     */
    @Transactional(readOnly = true)
    public Page<ClubBoardDto> getClubBoard(Pageable pageable,long clubBoardId) {
        Page<ClubBoard> byId = clubBoardRepository.findById(pageable,clubBoardId);
        return byId.map(e -> new ClubBoardDto(clubBoardId));
    }


    /**
     * 동호회 게시판 생성
     * 해당 동호회에 가입한 유저만이 게시판에 글을 쓸 수 있음
     *
     */
    @Transactional(readOnly = true)
    public ClubBoardDto createClubBoard(ClubBoardDto dto, User user) {

        User findUser = userRepository.findById(user.getId()).orElseThrow(()->{
            return new IllegalArgumentException("실패");
        });

        Club club = clubRepository.findById(dto.getClub().getId()).orElseThrow(()->{
            return new IllegalArgumentException("동호회 찾기 실패");
        });

        ClubBoard cb = dto.toEntity(findUser, club);
        clubBoardRepository.save(cb);
        return dto;
    }

}
