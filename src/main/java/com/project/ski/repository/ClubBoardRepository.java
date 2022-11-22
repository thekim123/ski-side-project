package com.project.ski.repository;

import com.project.ski.domain.club.ClubBoard;
import com.project.ski.web.dto.ClubBoardDto;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClubBoardRepository extends JpaRepository<ClubBoard, Long> {

    ClubBoardDto findById(long clubBoardId);
}
