package com.ski.backend.repository;

import com.ski.backend.domain.club.Club;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClubRepository extends JpaRepository<Club, Long> {
}
