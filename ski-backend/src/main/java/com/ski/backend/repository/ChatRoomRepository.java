package com.ski.backend.repository;

import com.ski.backend.club.entity.Club;
import com.ski.backend.domain.user.ChatRoom;
import com.ski.backend.domain.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ChatRoomRepository extends JpaRepository<ChatRoom, Long> {

    List<ChatRoom> findByUser(User user);

    Optional<ChatRoom> findByUserAndClub(User user, Club club);

    List<ChatRoom> findByClub(Club club);
}
