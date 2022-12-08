package com.project.ski.repository;

import com.project.ski.domain.club.ClubBoard;
import com.project.ski.domain.club.Reply;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface ReplyRepository extends JpaRepository<Reply,Long> {

    List<Reply> findByClubBoard(ClubBoard clubBoard);


}
