package com.project.ski.web.api;


import com.project.ski.config.auth.PrincipalDetails;
import com.project.ski.domain.club.Reply;
import com.project.ski.domain.user.User;
import com.project.ski.service.ReplyService;
import com.project.ski.web.dto.ClubBoardDto;
import com.project.ski.web.dto.CmRespDto;
import com.project.ski.web.dto.ReplyDto;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/clubBoard")
public class ReplyApiController {


    private final ReplyService replyService;

    @PostMapping("/reply")
    public CmRespDto<ReplyDto> saveReply(@RequestBody ReplyDto dto, Authentication auth) {
        PrincipalDetails principalDetails = (PrincipalDetails) auth.getPrincipal();
        User user = principalDetails.getUser();
        ReplyDto replyDto = replyService.saveReply(dto, user);
        return new CmRespDto<ReplyDto>(1, "동호회 댓글 작성 완료", replyDto);
    }

    //
    @DeleteMapping("/deleteReply/{replyId}")
    public CmRespDto<ClubBoardDto> deleteReply(@PathVariable long replyId) {
        replyService.deleteReply(replyId);
        return new CmRespDto<>(1, "동호회 댓글 삭제 완료", null);
    }

    @PutMapping("/updateReply/{replyId}")
    public CmRespDto<ReplyDto> updateReply(@PathVariable long replyId,@RequestBody ReplyDto replyDto) {
        ReplyDto dto = replyService.updateReply(replyId, replyDto);
        return new CmRespDto<>(1, "동호회 댓글 수정 완료",dto);
    }
}


