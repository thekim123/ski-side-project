package com.ski.backend.service;

import com.ski.backend.config.auth.PrincipalDetails;
import com.ski.backend.domain.user.ChatRoom;
import com.ski.backend.domain.user.User;
import com.ski.backend.domain.user.Whisper;
import com.ski.backend.repository.ChatRoomRepository;
import com.ski.backend.repository.WhisperRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    public User getPrincipal(Authentication authentication) {
        PrincipalDetails principalDetails = (PrincipalDetails) authentication.getPrincipal();
        return principalDetails.getUser();
    }
}
