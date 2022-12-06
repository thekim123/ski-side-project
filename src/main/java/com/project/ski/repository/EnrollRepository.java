package com.project.ski.repository;

import com.project.ski.domain.club.Enroll;
import com.project.ski.web.dto.EnrollRespDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface EnrollRepository extends JpaRepository<Enroll,Long> {


//    @Modifying
//    @Query(value = "insert into Enroll(userId,clubId,createDt,state) values(:userId,:clubId,now(),0)", nativeQuery = true)
//    EnrollRespDto enroll(@Param("userId") long userId,@Param("clubId") long clubId);


}
