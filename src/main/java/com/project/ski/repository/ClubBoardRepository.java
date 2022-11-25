package com.project.ski.repository;

import com.project.ski.domain.club.ClubBoard;
import com.project.ski.web.dto.ClubBoardDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ClubBoardRepository extends JpaRepository<ClubBoard, Long> {

    @Query(value = "select cb from ClubBoard cb where cb.id = :clubBoardId",
    countQuery = "select count (cb) from ClubBoard cb where cb.id = :clubBoardId")
    Page<ClubBoard> findById(Pageable pageable,@Param("clubBoardId") long clubBoardId);
}
