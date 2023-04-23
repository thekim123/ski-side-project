package com.ski.backend.service;

import com.ski.backend.config.auth.PrincipalDetails;
import com.ski.backend.club.entity.Club;
import com.ski.backend.user.entity.ChatRoom;
import com.ski.backend.user.entity.User;
import com.ski.backend.user.entity.Whisper;
import com.ski.backend.handler.ex.CustomApiException;
import com.ski.backend.repository.ChatRoomRepository;
import com.ski.backend.club.repository.ClubRepository;
import com.ski.backend.user.repository.UserRepository;
import com.ski.backend.repository.WhisperRepository;
import lombok.RequiredArgsConstructor;
import org.json.simple.JSONObject;
import org.springframework.http.*;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class ChatService {

    private final ChatRoomRepository chatRoomRepository;
    private final WhisperRepository whisperRepository;
    private final UserRepository userRepository;
    private final ClubRepository clubRepository;

    @Transactional(readOnly = true)
    public List<Whisper> getWhispers(Authentication authentication) {
        User user = ((PrincipalDetails) authentication.getPrincipal()).getUser();
        return whisperRepository.findByPrincipal(user);
    }

    @Transactional(readOnly = true)
    public List<ChatRoom> getChatRoom(Authentication authentication) {
        User user = ((PrincipalDetails) authentication.getPrincipal()).getUser();
        return chatRoomRepository.findByUser(user);
    }

    @Transactional
    public void deleteWhisper(Authentication authentication, Long whisperId) {
        User user = ((PrincipalDetails) authentication.getPrincipal()).getUser();

        Whisper whisper = whisperRepository.findById(whisperId).orElseThrow(() -> {
            throw new CustomApiException("해당 카풀 채팅 링크를 찾을 수 없습니다.");
        });

        String toUsername = whisper.getToUsername();
        User toUser = userRepository.findByUsername(toUsername).orElseThrow(() -> {
            throw new EntityNotFoundException("존재하지 않는 회원입니다.");
        });

        String toUserNickname = toUser.getNickname();
        toUserNickname = toUserNickname.split("_")[0];

        String msg = toUserNickname + "님이 퇴장하셨습니다.";
        sendSystemMessage(user.getUsername(), toUsername, msg, null);
        whisperRepository.deleteById(whisperId);
    }

    @Transactional
    public void deleteChatRoomWhenLeave(Long userId, long clubId) {
        Club club = clubRepository.findById(clubId).orElseThrow(() -> {
            throw new CustomApiException("동호회를 찾을 수 없습니다.");
        });

        User user = userRepository.findById(userId).orElseThrow(() -> {
            throw new CustomApiException("해당 유저를 찾을 수 없습니다.");
        });

        ChatRoom room = chatRoomRepository.findByUserAndClub(user, club).orElseThrow(() -> {
            throw new CustomApiException("채팅방이 존재하지 않습니다.");
        });

        chatRoomRepository.deleteById(room.getId());
    }

    @Transactional
    public void deleteChatRoom(Authentication authentication, Long chatRoomId) {
        User user = ((PrincipalDetails) authentication.getPrincipal()).getUser();

        String fakeNickname = user.getNickname().split("_")[0];
        String msg = fakeNickname + "님이 퇴장하셨습니다.";

        ChatRoom chatRoom = chatRoomRepository.findById(chatRoomId).orElseThrow(() -> {
            throw new CustomApiException("채팅방이 존재하지 않습니다.");
        });

        sendSystemMessage(user.getUsername(), null, msg, chatRoom.getRoomName());
        chatRoomRepository.deleteById(chatRoomId);
    }

    // 채팅방 퇴장 알림 메세지를 채팅방에 전송합니다.
    public void sendSystemMessage(String userNickname, String receiver, String msg, String roomName) {
        RestTemplate rt = new RestTemplate();
        String sseServerAddr = "http://15.165.81.194:8040/chat/save";

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("sender", userNickname);
        jsonObject.put("receiver", receiver);
        jsonObject.put("msg", msg);
        jsonObject.put("roomName", roomName);

        System.out.println(jsonObject);

        HttpEntity<Map<String, Object>> entity = new HttpEntity<>(jsonObject, headers);

        String response = rt.postForObject(
                sseServerAddr,
                entity,
                String.class
        );

        System.out.println(response);
    }

    @Transactional
    public void deleteAllChatRoomWhenDeleteClub(long clubId) {
        Club club = clubRepository.findById(clubId).orElseThrow(() -> {
            throw new CustomApiException("클럽의 고유 번호를 찾을 수가 없습니다. - 존재하지 않는 클럽입니다.");
        });

        var clubChatRooms = chatRoomRepository.findByClub(club);
        chatRoomRepository.deleteAll(clubChatRooms);
    }
}
