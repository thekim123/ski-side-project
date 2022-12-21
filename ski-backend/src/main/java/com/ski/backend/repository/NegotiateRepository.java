package com.ski.backend.repository;

import com.ski.backend.domain.carpool.Negotiate;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NegotiateRepository extends JpaRepository<Negotiate, Long> {
    Negotiate findByCarpoolId(Long carpoolId);
}
