package com.ski.backend.repository;

import com.ski.backend.domain.user.Whisper;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WhisperRepository extends JpaRepository<Whisper, Long> {
}
