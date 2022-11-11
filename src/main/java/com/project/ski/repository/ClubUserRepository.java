package com.project.ski.repository;

import com.project.ski.domain.club.Club;
import com.project.ski.domain.club.ClubUser;
import com.project.ski.domain.user.User;
import com.project.ski.web.dto.ClubUserRespDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface ClubUserRepository extends JpaRepository<ClubUser, Long> {

    @Query(value = "select cu from ClubUser cu join fetch cu.user u where cu.club.id = :clubId"
            ,countQuery = "select count(cu) from ClubUser cu  where cu.club.id = :clubId")
    Page<ClubUser> findByUser_Id(Pageable pageable, @Param("clubId") Long clubId);

    @Query("select cu from ClubUser cu where cu.user.id = :userId and cu.club.id = :clubId")
    Optional<ClubUser> findByUserIdAndClubId(@Param("userId") long userId,@Param("clubId") long clubId);
}
