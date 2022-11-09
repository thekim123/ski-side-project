package com.project.ski.repository;

import com.project.ski.domain.club.Club;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;




public interface ClubRepository extends JpaRepository<Club,Long> {

    @Query(value = "select c from Club c join fetch c.resort r where c.tempFlag = :tempFlag and c.user.id = :userId"
    ,countQuery = "select count(c) from Club c  where c.tempFlag = :tempFlag and c.user.id = :userId")
    Page<Club> findByTempFlagAndUser_Id(Pageable pageable, @Param("userId") Long userId, @Param("tempFlag") String tempFlag);

    @Query(value = "delete from Club c where c.user.id = :userId")
    void deleteByUser_Id(@Param("userId") Long userId);
}
