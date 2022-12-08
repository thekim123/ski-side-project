package com.project.ski.repository;

import com.project.ski.domain.club.Club;
import com.project.ski.domain.club.Enroll;
import com.project.ski.web.dto.EnrollRespDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface EnrollRepository extends JpaRepository<Enroll,Long> {
    List<Enroll> findByClub(Club club);



}
