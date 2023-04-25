package com.skiproject.sseserver.chat.service;

import com.skiproject.sseserver.chat.repository.ChatRepository;
import com.skiproject.sseserver.web.dto.ChatResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.scheduler.Schedulers;

@Service
@RequiredArgsConstructor
public class ChatService {
    private final ChatRepository chatRepository;

    public Flux<ChatResponseDto> getMessageByRoomName(String roomName) {
        return null;
    }

}
