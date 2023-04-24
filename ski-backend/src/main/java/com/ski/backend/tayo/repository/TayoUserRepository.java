package com.ski.backend.tayo.repository;

import com.ski.backend.tayo.entity.Role;
import com.ski.backend.tayo.entity.TayoUser;
import com.ski.backend.user.entity.Status;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface TayoUserRepository extends JpaRepository<TayoUser, Long> {
    @Query(value = "select tu from TayoUser tu where tu.user.id = :userId and tu.tayo.id = :tayoId and tu.role = :role")
    Optional<TayoUser> findByTayoId(@Param("tayoId") long tayoId, @Param("userId") long userId, @Param("role") Role role);

    @Query(value = "select tu from TayoUser tu where tu.tayo.id = :tayoId and tu.user.id = :userId")
    List<TayoUser> findByTayo_IdAndUser_Id(@Param("tayoId") long tayoId, @Param("userId") long userId);

    @Query(value = "select tu from TayoUser tu join tu.tayo t where t.id = :tayoId and tu.status = :status")
    List<TayoUser> findByTayoIdAndStatus(@Param("tayoId") long tayoId,@Param("status") Status status);

    @Query(value = "select tu.user.id from TayoUser tu where tu.tayo.id = :tayoId")
    Long findByTayoId(@Param("tayoId") long tayoId);

    @Query(value = "select tu from TayoUser tu where tu.user.id = :userId")
    List<TayoUser> findByUserId(@Param("userId") long userId);

    @Query(value = "select tu from TayoUser tu where tu.tayo.id = :tayoId")
    List<TayoUser> findByTayoIds(@Param("tayoId") long tayoId);
}
