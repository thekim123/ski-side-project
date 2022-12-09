package com.ski.backend.repository;

import com.ski.backend.domain.club.Club;
import com.ski.backend.domain.club.Enroll;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface EnrollRepository extends JpaRepository<Enroll,Long> {
    List<Enroll> findByClub(Club club);



}
