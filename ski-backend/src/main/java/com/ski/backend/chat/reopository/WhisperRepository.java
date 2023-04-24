package com.ski.backend.chat.reopository;

import com.ski.backend.user.entity.User;
import com.ski.backend.chat.entity.Whisper;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface WhisperRepository extends JpaRepository<Whisper, Long> {

    List<Whisper> findByPrincipal(User user);
}
