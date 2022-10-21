package com.project.ski.repository;

import com.project.ski.domain.board.Board;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface BoardRepository extends JpaRepository<Board, Long> {

    @Query(value = "select * FROM board where ResortId IN (select toResortId from bookmark where fromUserId = :principalId) order by desc", nativeQuery = true)
    Page<Board> SortByResortName(long principalId, Pageable pageable);

    @Query(value = "SELECT b.* FROM board b " +
            "INNER JOIN (SELECT boardId, COUNT(boardId) likeCount FROM likes GROUP BY boardId) c " +
            "ON b.id = c.boardId ORDER BY likeCount DESC", nativeQuery = true)
    Page<Board> getPopular();
}
