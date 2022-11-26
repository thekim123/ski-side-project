package com.project.ski.service;

import com.project.ski.config.auth.PrincipalDetails;
import com.project.ski.domain.board.Board;
import com.project.ski.domain.board.Comment;
import com.project.ski.domain.user.User;
import com.project.ski.handler.ex.CustomApiException;
import com.project.ski.repository.BoardRepository;
import com.project.ski.repository.CommentRepository;
import com.project.ski.web.dto.CommentDto;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;
    private final BoardRepository boardRepository;

    @Transactional
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

    @Transactional
    public void delete(long id, Authentication authentication) {
        long principalId = getPrincipalId(authentication);

        Comment commentEntity = commentRepository.findById(id).orElseThrow(() -> {
            throw new CustomApiException("존재하지 않는 댓글입니다.");
        });

        if (isMyComment(id, principalId)) {
            throw new CustomApiException("선생님 댓글이 아닙니다!!");
        }

        commentRepository.deleteById(id);
    }

    @Transactional
    public void update(CommentDto dto, Authentication authentication) {
        long principalId = getPrincipalId(authentication);
        long commentId = dto.getCommentId();

        Comment commentEntity = commentRepository.findById(commentId).orElseThrow(() -> {
            throw new CustomApiException("존재하지 않는 댓글입니다.");
        });

        if (isMyComment(commentId, principalId)) {
            throw new CustomApiException("선생님 댓글이 아닙니다!!");
        }

        commentEntity.setContent(dto.getContent());
    }

    public boolean isMyComment(long principalId, long commentId) {
        return principalId == commentId;
    }

    public long getPrincipalId(Authentication authentication) {
        PrincipalDetails principalDetails = (PrincipalDetails) authentication.getPrincipal();
        long principalId = principalDetails.getUser().getId();
        return principalId;
    }

}
