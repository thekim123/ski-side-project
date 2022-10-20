package com.project.ski.service;

import com.project.ski.domain.board.Board;
import com.project.ski.domain.user.User;
import com.project.ski.repository.BoardRepository;
import com.project.ski.web.dto.BoardRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class BoardService {

    private final BoardRepository boardRepository;

    @Transactional
    public void write(Board board, User user) {
        board.setUser(user);
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
    public void update(long boardId, BoardRequestDto dto) {
        Board boardEntity = boardRepository.findById(boardId).orElseThrow(() -> {
            return new IllegalArgumentException("글 수정 실패 : 게시글의 ID를 찾을 수 없습니다.");
        });

        boardEntity.setTitle(dto.getTitle());
        boardEntity.setContent(dto.getContent());
    }

    @Transactional(readOnly = true)
    public Page<Board> getBoardList(Pageable pageable, long principalId) {
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


}
