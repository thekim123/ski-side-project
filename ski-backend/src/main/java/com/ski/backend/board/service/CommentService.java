package com.ski.backend.board.service;

import com.ski.backend.config.auth.PrincipalDetails;
import com.ski.backend.board.entity.Board;
import com.ski.backend.board.entity.Comment;
import com.ski.backend.user.entity.User;
import com.ski.backend.board.repository.BoardRepository;
import com.ski.backend.board.repository.CommentRepository;
import com.ski.backend.board.dto.CommentDto;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;
    private final BoardRepository boardRepository;

    @Transactional
    public void write(User principal, CommentDto dto) {
        Board boardEntity = boardRepository.findById(dto.getBoardId()).orElseThrow(() -> {
            throw new EntityNotFoundException("게시글을 찾을 수 없습니다.");
        });

        Comment comment = Comment.builder()
                .content(dto.getContent())
                .board(boardEntity)
                .user(principal)
                .build();

        commentRepository.save(comment);
    }

    /**
     * @param commentId      댓글 id
     * @param authentication 로그인 정보
     * @author thekim123
     * @apiNote 치명적인 버그 수정.<br/>
     * 0.x 버전에서는 comment id가 user id와 같으면 삭제가 불가했음. <br/>
     * 이외에는 삭제가 다 되었음. <br/>
     * 즉, 자기 댓글이 아니어도 삭제할 수 있었음.<br/>
     * @since last modified at 2023.04.26
     */
    @Transactional
    public void delete(long commentId, Authentication authentication) {
        User principal = ((PrincipalDetails) authentication.getPrincipal()).getUser();
        Comment comment = commentRepository.findById(commentId).orElseThrow(() -> {
            throw new EntityNotFoundException("존재하지 않는 댓글입니다.");
        });
        if (isCommentOwner(comment, principal)) {
            throw new AccessDeniedException("댓글 작성자만이 삭제할 수 있습니다.");
        }

        commentRepository.deleteById(commentId);
    }

    public boolean isCommentOwner(Comment comment, User user) {
        return Objects.equals(comment.getUser().getUsername(), user.getUsername());
    }

}
