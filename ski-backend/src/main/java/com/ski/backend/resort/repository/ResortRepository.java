package com.ski.backend.resort.repository;

import com.ski.backend.resort.entity.Resort;
import com.ski.backend.resort.entity.ResortName;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ResortRepository extends JpaRepository<Resort, Long> {
    Optional<Resort> findByResortName(ResortName resortName);

    @Override
    Optional<Resort> findById(Long id);
}
