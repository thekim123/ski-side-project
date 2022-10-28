package com.project.ski.repository;

import com.project.ski.domain.carpool.Carpool;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CarpoolRepository extends JpaRepository<Carpool, Long> {
}
