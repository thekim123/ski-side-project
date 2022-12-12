package com.ski.backend.repository;

import com.ski.backend.domain.club.Club;
import com.ski.backend.domain.club.ClubBoard;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ClubBoardRepository extends JpaRepository<ClubBoard, Long> {

    @Query(value = "select cb from ClubBoard cb where cb.id = :clubBoardId",
    countQuery = "select count (cb) from ClubBoard cb where cb.id = :clubBoardId")
    Page<ClubBoard> findById(Pageable pageable,@Param("clubBoardId") long clubBoardId);

    @Query(value = "select cb from ClubBoard cb where cb.club.id = :clubId",
            countQuery = "select count(cb) from ClubBoard  cb where cb.club.id = :clubId")
    Page<ClubBoard> findByClubId(Pageable pageable, @Param("clubId") long clubId);


    List<ClubBoard> findByClub(Club club);

    @Query("select cb from ClubBoard cb join fetch cb.club c where cb.id = :clubBoardId")
    Optional<ClubBoard> findClubBoardById(@Param("clubBoardId") Long clubBoardId);
}
