package com.project.ski.repository;

import com.project.ski.domain.resort.Resort;
import com.project.ski.domain.resort.ResortName;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ResortRepository extends JpaRepository<Resort, Long> {
    Resort findByResortName(ResortName resortName);
    
    @Override
    Optional<Resort> findById(Long id);
}
