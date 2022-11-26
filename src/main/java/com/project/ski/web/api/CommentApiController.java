package com.project.ski.web.api;

import com.project.ski.config.auth.PrincipalDetails;
import com.project.ski.domain.user.User;
import com.project.ski.service.CommentService;
import com.project.ski.web.dto.CmRespDto;
import com.project.ski.web.dto.CommentDto;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/board/comment")
public class CommentApiController {

    private final CommentService commentService;


    @PostMapping("/write")
    public CmRespDto<?> writeComment(
            @RequestBody CommentDto dto,
            Authentication authentication) {
        PrincipalDetails principalDetails = (PrincipalDetails) authentication.getPrincipal();
        User principal = principalDetails.getUser();
        commentService.write(principal, dto);
        return new CmRespDto<>(1, "댓글쓰기 완료", null);
    }

    @DeleteMapping("/delete/{id}")
    public CmRespDto<?> delete(@PathVariable long id, Authentication authentication) {
        PrincipalDetails principalDetails = (PrincipalDetails) authentication.getPrincipal();
        User user = principalDetails.getUser();
        commentService.delete(id, user);
        return new CmRespDto<>(1, "댓글 삭제 완료",null);
    }

}
