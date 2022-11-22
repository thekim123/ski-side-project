package com.project.ski.web.dto;

import com.project.ski.domain.club.ClubBoard;
import com.project.ski.domain.user.User;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ReplyDto {

    // 댓글 식별값
    private Long id;

    // 동호회 게시판
    private ClubBoard clubBoard;

    // 사용자
    private User user;

    // 댓글
    private String reply;

    // 작성시간
    private LocalDateTime createDt;

    // 수정시간
    private LocalDateTime updateDt;

    public ReplyDto(Long id, ClubBoard clubBoard, User user, String reply, LocalDateTime createDt, LocalDateTime updateDt) {
        this.id = id;
        this.clubBoard = clubBoard;
        this.user = user;
        this.reply = reply;
        this.createDt = createDt;
        this.updateDt = updateDt;
    }
}
