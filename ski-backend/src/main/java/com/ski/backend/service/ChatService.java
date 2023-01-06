package com.ski.backend.service;

import com.ski.backend.config.auth.PrincipalDetails;
import com.ski.backend.domain.user.ChatRoom;
import com.ski.backend.domain.user.User;
import com.ski.backend.domain.user.Whisper;
import com.ski.backend.handler.ex.CustomApiException;
import com.ski.backend.repository.ChatRoomRepository;
import com.ski.backend.repository.WhisperRepository;
import com.ski.backend.web.dto.ChatDto;
import lombok.RequiredArgsConstructor;
import org.json.simple.JSONObject;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ChatService {

    private final ChatRoomRepository chatRoomRepository;
    private final WhisperRepository whisperRepository;

    @Transactional(readOnly = true)
    public List<Whisper> getWhispers(Authentication authentication) {
        User user = getPrincipal(authentication);
        List<Whisper> whispers = whisperRepository.findByPrincipal(user);
        return whispers;
    }

    @Transactional(readOnly = true)
    public List<ChatRoom> getChatRoom(Authentication authentication) {
        User user = getPrincipal(authentication);
        List<ChatRoom> chatRooms = chatRoomRepository.findByUser(user);
        return chatRooms;
    }

    @Transactional
    public void deleteWhisper(Authentication authentication, Long whisperId) {
        User user = getPrincipal(authentication);

        Whisper whisper = whisperRepository.findById(whisperId).orElseThrow(() -> {
            throw new CustomApiException("해당 카풀 채팅 링크를 찾을 수 없습니다.");
        });

        String username = user.getUsername();
        String msg = username + "님이 퇴장하셨습니다.";
        String toUsername = whisper.getToUsername();
        sendSystemMessage(username, toUsername, msg);
        whisperRepository.deleteById(whisperId);
    }

    @Transactional
    public void deleteChatRoom(Authentication authentication, Long chatroomId) {
        chatRoomRepository.deleteById(chatroomId);
    }

    public User getPrincipal(Authentication authentication) {
        PrincipalDetails principalDetails = (PrincipalDetails) authentication.getPrincipal();
        return principalDetails.getUser();
    }

    // 채팅방 퇴장 알림 메세지를 채팅방에 전송합니다.
    public void sendSystemMessage(String username, String receiver, String msg) {
        try {
            RestTemplate rt = new RestTemplate();
            String sseServerAddr = "http://15.165.81.194:8040/chat/save";
            URL url = new URL(sseServerAddr);

            ChatDto dto = ChatDto.builder()
                    .sender(username)
                    .receiver(receiver)
                    .msg(msg)
                    .createdAt(LocalDateTime.now())
                    .build();

            HttpEntity<String> httpEntity = new HttpEntity<>(dto.toString());

            ResponseEntity<String> response = rt.exchange(
                    sseServerAddr,
                    HttpMethod.POST,
                    httpEntity,
                    String.class
            );

            System.out.println("실행완료");
        } catch (IOException io) {
            System.out.println(io.getMessage());
            System.out.println(io.getStackTrace());
        }

    }
}
