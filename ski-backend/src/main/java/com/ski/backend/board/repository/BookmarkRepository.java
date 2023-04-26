package com.ski.backend.board.repository;

import java.util.List;

import com.ski.backend.resort.entity.Resort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.ski.backend.board.entity.Bookmark;
import com.ski.backend.user.entity.User;

public interface BookmarkRepository extends JpaRepository<Bookmark, Long> {

    List<Bookmark> findByFromUser(User fromUser);

    void deleteByToResortAndFromUser(Resort toResort, User fromUser);

}
