package com.ski.backend.repository;

import com.ski.backend.domain.club.Club;
import com.ski.backend.domain.club.ClubBoard;
import com.ski.backend.domain.club.ClubUser;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ClubUserRepository extends JpaRepository<ClubUser, Long> {

    @Query(value = "select cu from ClubUser cu join fetch cu.user u where cu.club.id = :clubId"
            ,countQuery = "select count(cu) from ClubUser cu  where cu.club.id = :clubId")
    Page<ClubUser> findByUser_Id(Pageable pageable, @Param("clubId") long clubId);

    @Query("select cu from ClubUser cu where cu.user.id = :userId and cu.club.id = :clubId")
    Optional<ClubUser> findByUserIdAndClubId(@Param("userId") long userId,@Param("clubId") long clubId);

    List<ClubUser> findByClub(Club club);

    Optional<ClubUser> findByClubBoard(ClubBoard clubBoard);
}
