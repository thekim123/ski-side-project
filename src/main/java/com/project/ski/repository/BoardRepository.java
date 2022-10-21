package com.project.ski.repository;

import com.project.ski.domain.board.Board;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface BoardRepository extends JpaRepository<Board, Long> {

    Page<Board> findByResortId(long resortId, Pageable pageable);

    @Query(value = "SELECT b.* FROM board b INNER JOIN (SELECT boardId, COUNT(boardId) likeCount FROM likes GROUP BY boardId) c ON b.id = c.boardId ORDER BY likeCount DESC", nativeQuery = true)
    List<Board> getPopular();

    @Query(value = "select * from board where resortId in (select toResortId from bookmark where fromUserId = :principalId) ORDER BY id DESC ", nativeQuery = true)
    Page<Board> homeBoard(@Param("principalId") long principalId, Pageable pageable);
}
