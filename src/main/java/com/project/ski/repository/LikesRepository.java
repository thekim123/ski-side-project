package com.project.ski.repository;

import com.project.ski.domain.board.Likes;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface LikesRepository extends JpaRepository<Likes, Long> {

    @Modifying
    @Query(value = "insert into likes(boardId, userId) values(:boardId, :principalId)", nativeQuery = true)
    int mLike(long boardId, long principalId);

    @Modifying
    @Query(value = "delete from likes where boardId=:boardId and userId=:principalId", nativeQuery = true)
    int mUnlike(long boardId, long principalId);
}
