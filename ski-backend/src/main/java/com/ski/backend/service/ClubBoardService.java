package com.ski.backend.service;


import com.ski.backend.domain.club.Club;
import com.ski.backend.domain.club.ClubBoard;
import com.ski.backend.domain.club.ClubUser;
import com.ski.backend.domain.user.User;
import com.ski.backend.repository.ClubBoardRepository;
import com.ski.backend.repository.ClubRepository;
import com.ski.backend.repository.ClubUserRepository;
import com.ski.backend.repository.UserRepository;
import com.ski.backend.web.dto.ClubBoardDto;
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
    private final ClubUserRepository clubUserRepository;

    /**
     * 동호회 게시판
     * 상세 조회 -{boardId}
     */
    @Transactional(readOnly = true)
    public Page<ClubBoardDto> getClubBoard(Pageable pageable,long clubBoardId) {
        Page<ClubBoard> byId = clubBoardRepository.findById(pageable,clubBoardId);
        return byId.map(ClubBoardDto::new);
    }

    /**
     * 동호회 게시판 전체 조회
     */
    @Transactional
    public Page<ClubBoardDto> getAllClubBoard(Pageable pageable, long clubId,User user) {
        Page<ClubBoard> clubBoard = clubBoardRepository.findByClubId(pageable, clubId);
        return clubBoard.map(clubBoard1 -> new ClubBoardDto(clubBoard1,user));
    }


    /**
     * 동호회 게시판 생성
     * 해당 동호회에 가입한 유저만이 게시판에 글을 쓸 수 있음
     *
     */
    @Transactional
    public ClubBoardDto createClubBoard(ClubBoardDto dto, User user) {

        User findUser = userRepository.findById(user.getId()).orElseThrow(()->{
            return new IllegalArgumentException("실패");
        });

        Club club = clubRepository.findById(dto.getClubId()).orElseThrow(()->{
            return new IllegalArgumentException("동호회 찾기 실패");
        });

        ClubBoard cb = dto.toEntity(user, club);
        clubBoardRepository.save(cb);
        ClubUser clubBoardUser = new ClubUser(cb, findUser);
        findUser.getClubUsers().add(clubBoardUser);
        clubBoardUser.setRole("관리자");
        return dto;
    }

    @Transactional
    public void update(long clubBoardId, ClubBoardDto dto) {
        ClubBoard boards = clubBoardRepository.findById(clubBoardId).orElseThrow(() -> new IllegalArgumentException("동호회 게시판 수정 실패"));
        boards.update(dto);
    }

    public void delete(long clubBoardId) {
        ClubBoard cb = clubBoardRepository.findById(clubBoardId).orElseThrow(() -> new IllegalArgumentException("동호회 게시판 삭제 완료"));
        ClubUser cu = clubUserRepository.findByClubBoard(cb).orElseThrow(() -> new IllegalArgumentException("동호회 게시판 유저 삭제 완료"));
        clubUserRepository.delete(cu);
        clubBoardRepository.delete(cb);
    }
}
