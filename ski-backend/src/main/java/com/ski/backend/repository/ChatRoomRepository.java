package com.ski.backend.repository;

import com.ski.backend.domain.user.ChatRoom;
import com.ski.backend.domain.user.User;
import com.ski.backend.domain.user.Whisper;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ChatRoomRepository extends JpaRepository<ChatRoom, Long> {

    List<ChatRoom> findByUser(User user);
    ChatRoom findByRoomName(String roomName);

}
