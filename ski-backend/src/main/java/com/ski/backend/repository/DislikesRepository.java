package com.ski.backend.repository;

import com.ski.backend.domain.board.Dislikes;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface DislikesRepository extends JpaRepository<Dislikes, Long> {

	@Modifying
	@Query(value = "insert into dislikes(boardId, userId) values(:boardId, :principalId)", nativeQuery = true)
	int mDislike(@Param("boardId") long boardId, @Param("principalId") long principalId);

	@Modifying
	@Query(value = "delete from dislikes where boardId=:boardId and userId=:principalId", nativeQuery = true)
	int mUnDislike(@Param("boardId") long boardId, @Param("principalId") long principalId);
}
