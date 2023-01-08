package com.ski.backend.service;

import com.ski.backend.config.auth.PrincipalDetails;
import com.ski.backend.domain.user.ChatRoom;
import com.ski.backend.domain.user.User;
import com.ski.backend.domain.user.Whisper;
import com.ski.backend.handler.ex.CustomApiException;
import com.ski.backend.repository.ChatRoomRepository;
import com.ski.backend.repository.UserRepository;
import com.ski.backend.repository.WhisperRepository;
import lombok.RequiredArgsConstructor;
import org.json.simple.JSONObject;
import org.springframework.http.*;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class ChatService {

    private final ChatRoomRepository chatRoomRepository;
    private final WhisperRepository whisperRepository;
    private final UserRepository userRepository;

    @Transactional(readOnly = true)
    public List<Whisper> getWhispers(Authentication authentication) {
        User user = getPrincipal(authentication);
        return whisperRepository.findByPrincipal(user);
    }

    @Transactional(readOnly = true)
    public List<ChatRoom> getChatRoom(Authentication authentication) {
        User user = getPrincipal(authentication);
        return chatRoomRepository.findByUser(user);
    }

    @Transactional
    public void deleteWhisper(Authentication authentication, Long whisperId) {
        User user = getPrincipal(authentication);

        Whisper whisper = whisperRepository.findById(whisperId).orElseThrow(() -> {
            throw new CustomApiException("해당 카풀 채팅 링크를 찾을 수 없습니다.");
        });

        String toUsername = whisper.getToUsername();
        User toUser = userRepository.findByUsername(toUsername);

        String toUserNickname = toUser.getNickname();
        toUserNickname = toUserNickname.split("_")[0];

        System.out.println(toUserNickname);

        String msg = toUserNickname + "님이 퇴장하셨습니다.";
        sendSystemMessage(user.getUsername(), toUsername, msg);
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
    public void sendSystemMessage(String userNickname, String receiver, String msg) {
        RestTemplate rt = new RestTemplate();
        String sseServerAddr = "http://15.165.81.194:8040/chat/save";

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("sender", userNickname);
        jsonObject.put("receiver", receiver);
        jsonObject.put("msg", msg);

        System.out.println(jsonObject);

        HttpEntity<Map<String, Object>> entity = new HttpEntity<>(jsonObject, headers);

        String response = rt.postForObject(
                sseServerAddr,
                entity,
                String.class
        );

        System.out.println(response);
    }
}
