package com.ski.backend.service;

import com.ski.backend.config.auth.PrincipalDetails;
import com.ski.backend.domain.board.Board;
import com.ski.backend.domain.resort.Resort;
import com.ski.backend.domain.resort.ResortName;
import com.ski.backend.domain.user.User;
import com.ski.backend.repository.BoardRepository;
import com.ski.backend.repository.ResortRepository;
import com.ski.backend.web.dto.BoardDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BoardService {

    private final BoardRepository boardRepository;
    private final ResortRepository resortRepository;

    @Transactional(readOnly = true)
    public Page<Board> getHomeBoard(Authentication authentication, Pageable pageable) {
        long principalId = getPrincipalId(authentication);
        Page<Board> boards = boardRepository.homeBoard(principalId, pageable);

        boards.forEach((board) -> {
            board.loadLikesAndDislikes(principalId);
        });
        return boards;
    }

    @Transactional
    public void write(BoardDto dto, User user) {
        Resort resort = findResort(dto);
        Board board = dto.toEntity(user, resort);
        boardRepository.save(board);
    }

    @Transactional
    public void delete(long boardId, Authentication authentication) {
        Board boardEntity = findBoardEntityById(boardId);
        long principalId = getPrincipalId(authentication);

        if (!isMyBoard(boardEntity.getUser().getId(), principalId)) {
            throw new IllegalArgumentException("선생님의 글이 아니잖아요!!!!");
        }

        boardRepository.delete(boardEntity);
    }

    @Transactional
    public void update(BoardDto dto, Authentication authentication) {
        long principalId = getPrincipalId(authentication);
        Board boardEntity = findBoardEntityById(dto.getId());

        if (!isMyBoard(boardEntity.getUser().getId(), principalId)) {
            throw new IllegalArgumentException("선생님의 글이 아니잖아요!!!!");
        }

        Resort resort = findResort(dto);
        boardEntity.changeData(dto, resort);

    }

    // 전체 게시글 보기
    @Transactional(readOnly = true)
    public Page<Board> getAllBoardList(Pageable pageable, long principalId) {
        Page<Board> boards = boardRepository.findAll(pageable);

        boards.forEach((board) -> {
            board.loadLikesAndDislikes(principalId);
        });

        return boards;
    }

    @Transactional(readOnly = true)
    public Page<Board> getBoardByResort(String resortName, Pageable pageable, Authentication authentication) {
        long principalId = getPrincipalId(authentication);
        ResortName name = ResortName.valueOf(resortName);
        Resort resort = resortRepository.findByResortName(name);
        long resortId = resort.getId();
        Page<Board> boards = boardRepository.findByResortId(resortId, pageable);

        boards.forEach((board) -> {
            board.loadLikesAndDislikes(principalId);
        });

        return boards;
    }

    // 인기게시글 보기(좋아요 순)
    @Transactional(readOnly = true)
    public List<Board> getPopular() {
        return boardRepository.getPopular();
    }

    @Transactional(readOnly = true)
    public Board getBoardDetail(long boardId, Authentication authentication) {
        long principalId = getPrincipalId(authentication);
        Board boardEntity = findBoardEntityById(boardId);
        boardEntity.loadLikesAndDislikes(principalId);
        return boardEntity;
    }


    /*
     *  아래는 트랜잭션 아닌 매소드
     */

    public Board findBoardEntityById(long boardId) {
        return boardRepository.findById(boardId).orElseThrow(() -> {
            return new IllegalArgumentException("게시글의 등록번호를 찾을 수 없습니다.");
        });
    }

    public long getPrincipalId(Authentication authentication) {
        PrincipalDetails principalDetails = (PrincipalDetails) authentication.getPrincipal();
        long principalId = principalDetails.getUser().getId();
        return principalId;
    }

    public boolean isMyBoard(long userId, long principalId) {
        return userId == principalId;
    }

    public Resort findResort(BoardDto dto) {
        ResortName resortName = ResortName.valueOf(dto.getResortName());
        Resort resort = resortRepository.findByResortName(resortName);
        return resort;
    }


}
