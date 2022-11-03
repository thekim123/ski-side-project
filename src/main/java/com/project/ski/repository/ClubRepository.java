package com.project.ski.repository;

import com.project.ski.domain.club.Club;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ClubRepository extends JpaRepository<Club,Long> {


}
