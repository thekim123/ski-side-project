package com.project.ski.repository;

import com.project.ski.domain.club.ClubUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface ClubUserRepository extends JpaRepository<ClubUser, Long> {

    @Query("select cu from ClubUser cu where cu.user.id = :userId and cu.club.id = :clubId")
    Optional<ClubUser> findByUserIdAndClubId(@Param("userId") long userId,@Param("clubId") long clubId);
}
