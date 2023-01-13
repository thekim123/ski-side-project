package com.ski.backend.web.api;

import com.ski.backend.config.auth.PrincipalDetails;
import com.ski.backend.domain.user.User;
import com.ski.backend.service.CommentService;
import com.ski.backend.web.dto.CmRespDto;
import com.ski.backend.web.dto.CommentDto;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/board/comment")
public class CommentApiController {

    private final CommentService commentService;


    @PostMapping("/write")
    public CmRespDto<?> writeComment(
            @Valid
            @RequestBody CommentDto dto,
            BindingResult bindingResult,
            Authentication authentication) {
        PrincipalDetails principalDetails = (PrincipalDetails) authentication.getPrincipal();
        User principal = principalDetails.getUser();
        commentService.write(principal, dto);
        return new CmRespDto<>(1, "댓글쓰기 완료", null);
    }

    @DeleteMapping("/delete/{commentId}")
    public CmRespDto<?> delete(@PathVariable long commentId, Authentication authentication) {
        commentService.delete(commentId, authentication);
        return new CmRespDto<>(1, "댓글 삭제 완료",null);
    }

}
