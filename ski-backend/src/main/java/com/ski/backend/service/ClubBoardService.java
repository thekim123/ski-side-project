package com.ski.backend.service;


import com.ski.backend.config.auth.PrincipalDetails;
import com.ski.backend.domain.club.Club;
import com.ski.backend.domain.club.ClubBoard;
import com.ski.backend.domain.club.ClubUser;
import com.ski.backend.domain.club.Reply;
import com.ski.backend.domain.user.User;
import com.ski.backend.handler.ex.CustomApiException;
import com.ski.backend.repository.*;
import com.ski.backend.web.dto.ClubBoardDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


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
    public Page<ClubBoardDto> getClubBoard(Pageable pageable, long clubBoardId) {
        Page<ClubBoard> byId = clubBoardRepository.findById(pageable,clubBoardId);
        return byId.map(ClubBoardDto::new);
    }

    /**
     * 동호회 게시판 전체 조회
     */
    @Transactional
    public Page<ClubBoardDto> getAllClubBoard(Pageable pageable, long clubId) {
        Page<ClubBoard> clubBoard = clubBoardRepository.findByClubId(pageable, clubId);
        return clubBoard.map(ClubBoardDto::new);
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

        ClubUser clubUser = clubUserRepository.findByClubId(club.getId(), findUser.getId(), "관리자").orElseThrow(() -> {
            return new CustomApiException("관리자만 게시판을 생성할 수 있습니다.");
        });

        ClubBoard cb = dto.toEntity(clubUser);
        clubBoardRepository.save(cb);

        return dto;
    }

    // 게시판 수정
    @Transactional
    public void update(long clubBoardId, ClubBoardDto dto, Authentication auth) {
        ClubBoard boards = clubBoardRepository.findById(clubBoardId).orElseThrow(() -> new IllegalArgumentException("동호회 게시판 수정 실패"));
        validateClubBoard(clubBoardId,auth);
        boards.update(dto);
    }

    // 게시판 삭제
    public void delete(long clubBoardId,Authentication auth) {
        ClubBoard cb = clubBoardRepository.findById(clubBoardId).orElseThrow(() -> new IllegalArgumentException("동호회 게시판 삭제 완료"));
        validateClubBoard(clubBoardId,auth);
        clubBoardRepository.delete(cb);
    }

    // 동호회 게시판 만든사람이 관리자인지 확인 해야댐
    public void validateClubBoard(long clubBoardId, Authentication auth){
        PrincipalDetails pd = (PrincipalDetails) auth.getPrincipal();
        String role = "관리자";
        ClubBoard cb = clubBoardRepository.findByClubBoardId(clubBoardId, pd.getUser().getId(), role).orElseThrow(() -> new CustomApiException("관리자만 동호회 게시판을 수정 / 삭제할 수 있습니다."));

        if(cb.getId() != clubBoardId){
            throw new CustomApiException("관리자만 동호회 게시판을 수정 / 삭제할 수 있습니다.");
        }
    }

}
