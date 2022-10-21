package com.project.ski.repository;

import com.project.ski.domain.board.Bookmark;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface BookmarkRepository extends JpaRepository<Bookmark, Long> {

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
