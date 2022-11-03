package com.project.ski.repository;

import com.project.ski.domain.Tayo.Tayo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface TayoRepository extends JpaRepository<Tayo, Long> {


    // 스키장별 목록 조회
    Page<Tayo> findByResortId(long resortId, Pageable pageable);

    @Query(value = "select * from Tayo t join resort r where r.resortName = :resortName",
            nativeQuery = true)
    List<Tayo> findByResortName(@Param("resortName") String resortName);
}

