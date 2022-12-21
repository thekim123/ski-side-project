package com.ski.backend.repository;

import com.ski.backend.domain.club.Club;
import com.ski.backend.web.dto.ClubResponseDto;
import org.springframework.core.convert.TypeDescriptor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;


public interface ClubRepository extends JpaRepository<Club,Long> {




}
