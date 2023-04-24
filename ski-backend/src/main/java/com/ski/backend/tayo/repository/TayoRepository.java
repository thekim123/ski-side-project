package com.ski.backend.tayo.repository;

import com.ski.backend.tayo.entity.Tayo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface TayoRepository extends JpaRepository<Tayo, Long> {


//    // 스키장별 목록 조회
//    Page<Tayo> findByResortId(long resortId, Pageable pageable);
//
    @Query(value = "select t from Tayo t join fetch Resort r on r.id = t.resort.id where r.id = :resortId")
    List<Tayo> findByResortId(@Param("resortId") long resortId);

    @Query(value = "select t, tu.user.id from Tayo t join TayoUser tu on t.id = tu.tayo.id  where tu.tayo.id = :tayoId ")
    Tayo findByTayoIds(@Param("tayoId") long tayoId);
}

