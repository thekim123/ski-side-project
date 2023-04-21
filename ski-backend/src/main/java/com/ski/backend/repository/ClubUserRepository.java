package com.ski.backend.repository;

import com.ski.backend.domain.club.ClubUser;
import com.ski.backend.domain.club.Role;
import com.ski.backend.domain.common.Status;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

/**
 * @author 김병조
 * @apiNote 2023.04.21 modified by thekim123 - JPA ORM 관련 리팩토링함
 * 아직 테스트는 안함 ㅎ
 */
public interface ClubUserRepository extends JpaRepository<ClubUser, Long> {

    Optional<ClubUser> findByUserIdAndClubIdAndStatus(@Param("userId") long userId, @Param("clubId") long clubId, @Param("status") Status status);

    Optional<ClubUser> findClubUserByClubId(@Param("clubId") long clubId, @Param("userId") long userId, @Param("role") Role role);

    Optional<ClubUser> findByClubIdAndUserId(@Param("clubId") long clubId, @Param("userId") long userId);

    List<ClubUser> findByUserId(long userId);


    // 동호회Id 별로 해당 동호회 유저 찾기
    @Query(value = "select cu" +
            " from ClubUser cu" +
            " where cu.club.id = :clubId"
            , countQuery = "select" +
            " count(cu) from ClubUser  cu " +
            "where cu.club.id = :clubId")
    Page<ClubUser> findByClub_Id(Pageable pageable, @Param("clubId") long clubId);

    // 사용자 Id 별로 동호회 찾기
    @Query(value = "select cu" +
            " from ClubUser cu " +
            "where cu.user.id = :userId"
            , countQuery = "select" +
            " count(cu) from ClubUser cu" +
            " where cu.user.id = :userId")
    Page<ClubUser> findByUser_Id(Pageable pageable, @Param("userId") long userId);


    // 동호회 Id, 사용자 Id에 따른 동호회 유저들 목록 찾기
    @Query(value = "select cu" +
            " from ClubUser cu" +
            " where cu.club.id =:clubId " +
            "and cu.user.id = :userId")
    List<ClubUser> findByClub_IdAndUser_Id(@Param("clubId") long clubId, @Param("userId") long userId);

    // 동호회 Id, 상태에 따른 동호회 유저 목록 찾기
    @Query(value = "select cu " +
            "from ClubUser cu " +
            "join cu.club c " +
            "where c.id = :clubId " +
            "and cu.status = :status")
    List<ClubUser> findByClubIdAndStatus(@Param("clubId") long clubId, @Param("status") Status status);

    // 동호회 Id로 동호회 유저들 찾기
    // TODO: 이거 @Query 지우고도 정상 작동하는지 테스트할 것 되지 않을까?
    @Query(value = "select cu " +
            "from ClubUser cu " +
            "join cu.club c " +
            "where c.id = :clubId ")
    List<ClubUser> findClubUserByClubId(@Param("clubId") long clubId);

}
