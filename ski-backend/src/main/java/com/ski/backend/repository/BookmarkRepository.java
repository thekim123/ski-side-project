package com.ski.backend.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.ski.backend.domain.board.Bookmark;
import com.ski.backend.domain.user.User;

public interface BookmarkRepository extends JpaRepository<Bookmark, Long> {

	List<Bookmark> findByFromUser(User fromUser);
	
	@Modifying
	@Query(value = "insert into bookmark(fromUserId, toResortId, createDate) values(:fromUserId, :toResortId, now())", nativeQuery = true)
	void customOnBookmark(@Param("fromUserId") long fromUserId, @Param("toResortId") long toResortId);

	@Modifying
	@Query(value = "delete from bookmark where fromUserId = :fromUserId and toResortId = :toResortId", nativeQuery = true)
	void customOffBookmark(@Param("fromUserId") long fromUserId, @Param("toResortId") long toResortId);

	@Query(value = "select count(*) from bookmark where fromUserId = :principalId and toResortId = :toResortId", nativeQuery = true)
	void customBookmarkState(@Param("principalId") long principalId, @Param("toResortId") long toResortId);

	@Query(value = "select count(*) from bookmark where toResortId = :toResortId", nativeQuery = true)
	void customBookmarkCount(@Param("toResortId") long toResortId);

}
