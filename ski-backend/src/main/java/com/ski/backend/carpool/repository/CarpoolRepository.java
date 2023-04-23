package com.ski.backend.carpool.repository;

import com.ski.backend.carpool.entity.Carpool;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CarpoolRepository extends JpaRepository<Carpool, Long> {
}
