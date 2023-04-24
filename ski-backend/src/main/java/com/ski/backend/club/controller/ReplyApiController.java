package com.ski.backend.club.controller;


import com.ski.backend.config.auth.PrincipalDetails;
import com.ski.backend.user.entity.User;
import com.ski.backend.club.repository.ReplyService;
import com.ski.backend.common.CmRespDto;
import com.ski.backend.club.dto.ReplyDto;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/clubBoard")
public class ReplyApiController {

    private final ReplyService replyService;

    @PostMapping("/reply")
    public CmRespDto<?> saveReply(@Valid @RequestBody ReplyDto dto, BindingResult bindingResult, Authentication auth) {
        User user = ((PrincipalDetails) auth.getPrincipal()).getUser();
        ReplyDto replyDto = replyService.saveReply(dto, user);
        return new CmRespDto<>(1, "동호회 댓글 작성 완료", replyDto);
    }

    //
    @DeleteMapping("/deleteReply/{replyId}")
    public CmRespDto<?> deleteReply(@PathVariable long replyId) {
        replyService.deleteReply(replyId);
        return new CmRespDto<>(1, "동호회 댓글 삭제 완료", null);
    }

    @PutMapping("/updateReply/{replyId}")
    public CmRespDto<?> updateReply(@PathVariable long replyId, @Valid @RequestBody ReplyDto replyDto, BindingResult bindingResult) {
        ReplyDto dto = replyService.updateReply(replyId, replyDto);
        return new CmRespDto<>(1, "동호회 댓글 수정 완료", dto);
    }
}


