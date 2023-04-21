package com.ski.backend.repository;

import com.ski.backend.domain.club.Reply;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReplyRepository extends JpaRepository<Reply, Long> {
}
