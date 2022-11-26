package com.project.ski.service;

import com.project.ski.config.auth.PrincipalDetails;
import com.project.ski.domain.board.Board;
import com.project.ski.domain.resort.Resort;
import com.project.ski.domain.resort.ResortName;
import com.project.ski.domain.user.User;
import com.project.ski.repository.BoardRepository;
import com.project.ski.repository.ResortRepository;
import com.project.ski.web.dto.BoardDto;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class BoardService {

    private final BoardRepository boardRepository;
    private final ResortRepository resortRepository;

    @Value("${file.path}")
    private String uploadFolder;

    @Transactional(readOnly = true)
    public Page<Board> getHomeBoard(Authentication authentication, Pageable pageable) {
        long principalId = getUserFromPrincipal(authentication).getId();
        Page<Board> boards = boardRepository.homeBoard(principalId, pageable);

        boards.forEach((board) -> {
            board.setLikeCount(board.getLikes().size());

            board.getLikes().forEach((like) -> {
                if (like.getUser().getId() == principalId) {
                    board.setLikeState(true);
                }
            });
        });
        return boards;
    }

    @Transactional
    public void write(BoardDto dto, User user) {
        ResortName resortName = ResortName.valueOf(dto.getResortName());
        Resort resort = resortRepository.findByResortName(resortName);
        Board board = dto.toEntity(user, resort);
        boardRepository.save(board);
    }

    @Transactional
    public void delete(long boardId) {
        Board boardEntity = boardRepository.findById(boardId).orElseThrow(() -> {
            return new IllegalArgumentException("글 삭제 실패 : 게시글의 ID를 찾을 수 없습니다.");
        });
        boardRepository.delete(boardEntity);
    }

    @Transactional
    public void update(BoardDto dto, Authentication authentication) {
        User user = getUserFromPrincipal(authentication);

        Board boardEntity = boardRepository.findById(dto.getId()).orElseThrow(() -> {
            return new IllegalArgumentException("글 수정 실패 : 게시글의 ID를 찾을 수 없습니다.");
        });

        ResortName resortName = ResortName.valueOf(dto.getResortName());
        Resort resort = resortRepository.findByResortName(resortName);
        boardEntity.setResort(resort);

    }

    // 전체 게시글 보기
    @Transactional(readOnly = true)
    public Page<Board> getAllBoardList(Pageable pageable, long principalId) {
        Page<Board> boards = boardRepository.findAll(pageable);

        boards.forEach((board) -> {
            board.setLikeCount(board.getLikes().size());

            board.getLikes().forEach((like) -> {
                if (like.getUser().getId() == principalId) {
                    board.setLikeState(true);
                }
            });
        });
        return boards;
    }

    @Transactional(readOnly = true)
    public Page<Board> getBoardByResort(String resortName, Pageable pageable) {
        ResortName name = ResortName.valueOf(resortName);
        Resort resort = resortRepository.findByResortName(name);
        long resortId = resort.getId();
        Page<Board> pages = boardRepository.findByResortId(resortId, pageable);
        return pages;
    }

    // 인기게시글 보기(좋아요 순)
    @Transactional(readOnly = true)
    public List<Board> getPopular() {
        return boardRepository.getPopular();
    }

    public User getUserFromPrincipal(Authentication authentication) {
        PrincipalDetails principalDetails = (PrincipalDetails) authentication.getPrincipal();
        return principalDetails.getUser();
    }

    @Transactional(readOnly = true)
    public Board getBoardDetail(long id, Authentication authentication) {

        PrincipalDetails principalDetails = (PrincipalDetails) authentication.getPrincipal();
        long principalId = principalDetails.getUser().getId();

        Board boardEntity = boardRepository.findById(id).orElseThrow(() -> {
            return new IllegalArgumentException("게시글의 등록번호를 찾을 수 없습니다.");
        });

        boardEntity.setLikeCount(boardEntity.getLikes().size());

        boardEntity.getLikes().forEach((like) -> {
            if (like.getUser().getId() == principalId) {
                boardEntity.setLikeState(true);
            }
        });

        return boardEntity;

    }

}
