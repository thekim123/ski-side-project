package com.ski.backend.repository.club;

import com.ski.backend.domain.club.Club;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClubRepository extends JpaRepository<Club, Long> {
}
