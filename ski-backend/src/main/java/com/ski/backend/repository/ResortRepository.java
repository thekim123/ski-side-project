package com.ski.backend.repository;

import com.ski.backend.domain.resort.Resort;
import com.ski.backend.domain.resort.ResortName;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ResortRepository extends JpaRepository<Resort, Long> {
    Resort findByResortName(ResortName resortName);
    
    @Override
    Optional<Resort> findById(Long id);
}
