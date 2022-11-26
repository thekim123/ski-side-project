package com.project.ski.service;

import com.project.ski.domain.board.Board;
import com.project.ski.domain.board.Comment;
import com.project.ski.domain.user.User;
import com.project.ski.repository.BoardRepository;
import com.project.ski.repository.CommentRepository;
import com.project.ski.web.dto.CommentDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;
    private final BoardRepository boardRepository;

    public void write(User principal, CommentDto dto) {
        Board boardEntity = boardRepository.findById(dto.getBoardId()).orElseThrow(() -> {
            return new IllegalArgumentException("게시글을 찾을 수 없습니다.");
        });

        Comment comment = Comment.builder()
                .content(dto.getContent())
                .board(boardEntity)
                .user(principal)
                .build();
        
        commentRepository.save(comment);
    }

    public void delete(long id, User user) {
        Comment commentEntity = commentRepository.findById(id).get();
        if (commentEntity.getUser().getId() == id) {
            commentRepository.deleteById(id);
        } else {
            throw new IllegalArgumentException("존재하지 않는 댓글 번호입니다.");
        }
    }

}
