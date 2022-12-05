package com.project.ski.repository;

import com.project.ski.domain.carpool.Negotiate;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NegotiateRepository extends JpaRepository<Negotiate, Long> {
}
