package com.project.ski.web.api;


import com.project.ski.config.auth.PrincipalDetails;
import com.project.ski.domain.club.Reply;
import com.project.ski.service.ReplyService;
import com.project.ski.web.dto.ClubBoardDto;
import com.project.ski.web.dto.CmRespDto;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/club")
public class ReplyApiController {


    private final ReplyService replyService;

    @PostMapping("/{clubBoardId}/reply")
    public CmRespDto<?> saveReply(@PathVariable long clubBoardId, @RequestBody Reply reply, @AuthenticationPrincipal PrincipalDetails principalDetails) {
        replyService.saveReply(clubBoardId, reply, principalDetails.getUser());
        return new CmRespDto<>(1, "동호회 댓글 작성 완료", null);
    }
//
    @DeleteMapping("/{clubBoardId}/reply/{replyId}")
    public CmRespDto<ClubBoardDto> delteReply(@PathVariable long replyId) {
        replyService.deleteReply(replyId);
        return new CmRespDto<>(1, "동호회 댓글 삭제 완료", null);
    }
}


