package com.skiproject.sseserver.web;

import com.skiproject.sseserver.domain.Chat;
import com.skiproject.sseserver.repository.ChatRepository;
import com.skiproject.sseserver.web.dto.ChatRoomDto;
import com.skiproject.sseserver.web.dto.ChatWhisperDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/chat")
public class ChatController {

    private final ChatRepository chatRepository;

    @CrossOrigin
    @PostMapping("/save")
    public Mono<Chat> setMsg(@RequestBody Chat chat) {
        chat.setCreatedAt(LocalDateTime.now());
        return chatRepository.save(chat);
    }

    @CrossOrigin
    @GetMapping(value = "/room/{roomName}", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<Chat> findByRoomName(@PathVariable String roomName) {
        return chatRepository.mFindByRoomName(roomName).subscribeOn(Schedulers.boundedElastic());
    }

    @CrossOrigin
    @GetMapping(value = "/room", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<Chat> joinRoom(@RequestBody ChatRoomDto dto) {
        String roomName = dto.getRoomName();
        return chatRepository.mFindByRoomName(roomName).subscribeOn(Schedulers.boundedElastic());
    }

    @CrossOrigin
    @GetMapping(value = "/sender/{sender}/receiver/{receiver}", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<Chat> getMsg(@PathVariable String sender, @PathVariable String receiver) {
        return chatRepository.mFindBySender(sender, receiver)
                .subscribeOn(Schedulers.boundedElastic());
    }

    @CrossOrigin
    @GetMapping(value = "/whisper", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<Chat> getMsg(@RequestBody ChatWhisperDto dto) {
        String sender = dto.getSender();
        String receiver = dto.getReceiver();
        return chatRepository.mFindBySender(sender, receiver)
                .subscribeOn(Schedulers.boundedElastic());
    }

}
