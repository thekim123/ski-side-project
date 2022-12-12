package com.ski.backend.repository;

import com.project.ski.domain.club.Club;
import com.project.ski.domain.club.Enroll;
import com.project.ski.web.dto.EnrollRespDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface EnrollRepository extends JpaRepository<Enroll,Long> {
    List<Enroll> findByClub(Club club);

    @Query(value = "select e from Enroll e join fetch e.fromUser where e.club.id = :clubId "
    ,countQuery = "select count (e) from Enroll e where e.club.id = :clubId")
    Page<Enroll> findBy_ClubId(Pageable pageable, @Param("clubId") long clubId);

}
