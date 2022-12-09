package com.ski.backend.repository;

import com.ski.backend.domain.club.ClubBoard;
import com.ski.backend.domain.club.Reply;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ReplyRepository extends JpaRepository<Reply,Long> {

    List<Reply> findByClubBoard(ClubBoard clubBoard);


}
