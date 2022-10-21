package com.project.ski.web.dto;

import com.project.ski.domain.board.Board;
import com.project.ski.domain.resort.Resort;
import com.project.ski.domain.resort.ResortName;
import com.project.ski.domain.user.User;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@NoArgsConstructor
@Getter
public class BoardRequestDto {

    private Long id;
    private String title;
    private String content;
    private String username;
    private String resortName;
    private LocalDateTime createDate;

    public BoardRequestDto toDto(Board board) {
        this.id = board.getId();
        this.title = board.getTitle();
        this.content = board.getContent();
        this.username = board.getUser().getUsername();
        this.createDate = board.getCreateDate();
        this.resortName = board.getResortName().toString();
        return this;
    }
}
