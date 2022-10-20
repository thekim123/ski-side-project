package com.project.ski.web.dto;

import com.project.ski.domain.board.Board;
import com.project.ski.domain.user.User;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@NoArgsConstructor
@Getter
public class BoardDto {

    private Long id;
    private String title;
    private String content;
    private User user;
    private LocalDateTime createDate;

    public BoardDto toDto(Board board) {
        this.id = board.getId();
        this.title = board.getTitle();
        this.content = board.getContent();
        this.user = board.getUser();
        this.createDate = board.getCreateDate();
        return this;
    }
}
