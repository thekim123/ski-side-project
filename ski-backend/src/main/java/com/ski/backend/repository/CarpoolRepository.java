package com.ski.backend.repository;

import com.ski.backend.domain.carpool.Carpool;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CarpoolRepository extends JpaRepository<Carpool, Long> {
}
