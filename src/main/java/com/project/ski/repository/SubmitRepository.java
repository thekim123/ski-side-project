package com.project.ski.repository;

import com.project.ski.domain.carpool.Submit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface SubmitRepository extends JpaRepository<Submit, Long> {

    @Modifying
    @Query(value = "insert into submit(fromUserId, toCarpoolId, createDate, state) values(:fromUserId, :toCarpoolId, now(), 0)", nativeQuery = true)
    void mSubmit(@Param("fromUserId") long fromUserId, @Param("toCarpoolId") long toCarpoolId);

    @Modifying
    @Query(value = "delete from submit where fromUserId = :fromUserId and toCarpoolId = :toCarpoolId", nativeQuery = true)
    void mUnSubmit(@Param("fromUserId") long fromUserId, @Param("toCarpoolId") long toCarpoolId);

    List<Submit> findByToCarpoolId(long carpoolId);
}
