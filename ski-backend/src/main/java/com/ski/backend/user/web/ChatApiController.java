package com.ski.backend.user.web;

import com.ski.backend.service.ChatService;
import com.ski.backend.web.dto.CmRespDto;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

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

    @DeleteMapping("whisper/delete/{whisperId}")
    public CmRespDto<?> deleteWhisper(Authentication authentication, @PathVariable Long whisperId){
        chatService.deleteWhisper(authentication, whisperId);
        return new CmRespDto<>(1, "카풀 채팅 링크 삭제 완료", null);
    }

    // 개인의 채팅방 링크(목록)를 삭제
    @DeleteMapping("chatroom/delete/{chatroomId}")
    public CmRespDto<?> deleteChatRoom(Authentication authentication, @PathVariable Long chatroomId) {
        chatService.deleteChatRoom(authentication, chatroomId);
        return new CmRespDto<>(1, "카풀 채팅 링크 삭제 완료", null);
    }
}
