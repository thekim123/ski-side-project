package com.ski.backend.carpool.repository;

import com.ski.backend.carpool.entity.Submit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface SubmitRepository extends JpaRepository<Submit, Long> {

    @Modifying
    @Query(value = "insert into Submit(fromUserId, toCarpoolId, createDate, state) values(:fromUserId, :toCarpoolId, now(), 0)", nativeQuery = true)
    void mSubmit(@Param("fromUserId") long fromUserId, @Param("toCarpoolId") long toCarpoolId);

    @Modifying
    @Query(value = "delete from Submit where fromUserId = :fromUserId and toCarpoolId = :toCarpoolId", nativeQuery = true)
    void mUnSubmit(@Param("fromUserId") long fromUserId, @Param("toCarpoolId") long toCarpoolId);

    List<Submit> findByToCarpoolId(long carpoolId);
    Optional<Submit> findByFromUserIdAndToCarpoolId(long fromUserId, long toCarpoolId);
    List<Submit> findByFromUserId(long fromUserId);
}
