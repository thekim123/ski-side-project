package com.ski.backend.web.api;

import com.ski.backend.domain.user.Whisper;
import com.ski.backend.service.ChatService;
import com.ski.backend.web.dto.CmRespDto;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/chat")
@RequiredArgsConstructor
public class ChatApiController {

    private final ChatService chatService;

    @GetMapping("whisper")
    public CmRespDto<?> getWhispers(Authentication authentication) {
        return new CmRespDto<>(1, "카풀 채팅 조회 완료", chatService.getWhispers(authentication));
    }

    @GetMapping("chatroom")
    public CmRespDto<?> getChatRoom(Authentication authentication) {
        return new CmRespDto<>(1, "동호회 채팅 조회 완료", chatService.getChatRoom(authentication));
    }
}
