package com.project.ski.web.dto;

import com.project.ski.domain.club.ClubBoard;
import com.project.ski.domain.club.Reply;
import com.project.ski.domain.user.User;
import lombok.*;

import java.time.LocalDateTime;


@Data
public class ReplyDto {

    // 댓글 식별값
    private Long id;

    // 동호회 게시판
    private long clubBoardId;


    // 댓글
    private String reply;

    // 작성시간
    private LocalDateTime createDt;

    // 수정시간
    private LocalDateTime updateDt;


    public Reply toEntity(User user, ClubBoard clubBoard) {
        return Reply.builder()
                .user(user)
                .clubBoard(clubBoard)
                .reply(reply)

                .build();
    }

}
