package com.ski.backend.repository;

import com.ski.backend.domain.club.ClubUser;
import com.ski.backend.domain.common.Status;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ClubUserRepository extends JpaRepository<ClubUser, Long> {
    @Query(value="select cu" +
            " from ClubUser cu" +
            " where cu.club.id = :clubId"
            ,countQuery = "select" +
            " count(cu) from ClubUser  cu " +
            "where cu.club.id = :clubId")
    Page<ClubUser> findByClub_Id(Pageable pageable, @Param("clubId") long clubId);

    @Query(value = "select cu from ClubUser cu where cu.user.id = :userId and cu.club.id = :clubId and cu.status = :status")
    Optional<ClubUser> findByUserIdAndClubId(@Param("userId") long userId, @Param("clubId") long clubId,@Param("status") Status status);

    @Query(value = "select cu from ClubUser cu where cu.user.id = :userId"
            ,countQuery = "select count(cu) from ClubUser cu where cu.user.id = :userId")
    Page<ClubUser> findByUser_Id(Pageable pageable, @Param("userId") long userId);

//    @Query(value = "select cu " +
//            "from ClubUser cu " +
//            "join cu.club c " +
//            "join cu.user u " +
//            "where u.id = :userId " +
//            "and cu.id = :clubId " +
//            "and cu.role = :role")
    @Query(value = "select cu from ClubUser cu where cu.user.id = :userId and cu.club.id = :clubId and cu.role = :role")
    Optional<ClubUser> findByClubId(@Param("clubId") long clubId, @Param("userId") long userId, @Param("role") String role);

    @Query(value = "select cu from ClubUser cu where cu.club.id =:clubId and cu.user.id = :userId")
    List<ClubUser> findByClub_IdAndUser_Id(@Param("clubId") long clubId, @Param("userId") long userId);

    @Query(value = "select cu " +
            "from ClubUser cu " +
            "join cu.club c " +
            "where c.id = :clubId " +
            "and cu.status = :status")
    List<ClubUser> findByClubIdAAndStatus(@Param("clubId") long clubId, @Param("status") Status status);

    @Query(value = "select cu " +
            "from ClubUser cu " +
            "where cu.user.id =:userId")
    List<ClubUser> findByIdUserId(@Param("userId") long userId);


}
