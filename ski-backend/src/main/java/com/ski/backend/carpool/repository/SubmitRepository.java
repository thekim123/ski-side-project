package com.ski.backend.carpool.repository;

import com.ski.backend.carpool.entity.Submit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface SubmitRepository extends JpaRepository<Submit, Long> {

    /**
     * 카풀 신청 취소 repository 메서드
     *
     * @param fromUserId  신청자 user id
     * @param toCarpoolId 카풀 게시글 id
     * @apiNote 신청 api 의 경우에는  @Modifying 을 걷어냈지만 삭제의 경우에는 남겨두었다.
     * 이유는 현재 사용하는 컨트롤러의 파라메터가 많지도 않고,
     * 삭제를 하기 위해서 이곳저곳 들쑤시고 다니는게 귀찮아서 내버려 둠
     * @since last modified at 2023.04.24
     */
    @Modifying
    @Query(value = "delete from Submit where fromUserId = :fromUserId and toCarpoolId = :toCarpoolId", nativeQuery = true)
    void mUnSubmit(@Param("fromUserId") long fromUserId, @Param("toCarpoolId") long toCarpoolId);

    List<Submit> findByToCarpoolId(long carpoolId);

    Optional<Submit> findByFromUserIdAndToCarpoolId(long fromUserId, long toCarpoolId);

    List<Submit> findByFromUserId(long fromUserId);
}
