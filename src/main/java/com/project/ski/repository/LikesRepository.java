package com.project.ski.repository;

import com.project.ski.domain.board.Likes;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface LikesRepository extends JpaRepository<Likes, Long> {

	@Modifying
	@Query(value = "insert into likes(boardId, userId) values(:boardId, :principalId)", nativeQuery = true)
	int mLike(@Param("boardId") long boardId, @Param("principalId") long principalId);

	@Modifying
	@Query(value = "delete from likes where boardId=:boardId and userId=:principalId", nativeQuery = true)
	int mUnlike(@Param("boardId") long boardId, @Param("principalId") long principalId);
}
