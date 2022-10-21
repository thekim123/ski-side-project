package com.project.ski.repository;

import com.project.ski.domain.resort.Resort;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ResortRepository extends JpaRepository<Resort, Long> {
    Resort findByResortName(String resortName);
}
