package com.project.ski.service;

import com.project.ski.domain.board.Board;
import com.project.ski.domain.board.Comment;
import com.project.ski.domain.user.User;
import com.project.ski.repository.CommentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;

    public void write(User principal, String content, long boardId) {
        Board board = Board.builder()
                .id(boardId)
                .build();

        Comment comment = Comment.builder()
                .content(content)
                .board(board)
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
