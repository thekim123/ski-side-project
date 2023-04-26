package com.ski.backend.club.repository;


import com.ski.backend.club.entity.ClubBoard;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;


public interface ClubBoardRepository extends JpaRepository<ClubBoard, Long> {

    @Query(value = "select cb " +
            "from ClubBoard cb" +
            " where cb.id = :clubBoardId",
            countQuery = "select count (cb) " +
                    "from ClubBoard cb" +
                    " where cb.id = :clubBoardId")
    Page<ClubBoard> findById(Pageable pageable, @Param("clubBoardId") long clubBoardId);

    @Query(value = "select cb " +
            "from ClubBoard cb " +
            "join ClubUser cu on cb.clubUser.id = cu.id" +
            " where cu.club.id = :clubId",
            countQuery = "select count(cb) " +
                    "from ClubBoard  cb " +
                    "join ClubUser cu " +
                    "on cb.clubUser.id = cu.id" +
                    " where cu.club.id = :clubId")
    Page<ClubBoard> findByClubId(Pageable pageable, @Param("clubId") long clubId);

}
