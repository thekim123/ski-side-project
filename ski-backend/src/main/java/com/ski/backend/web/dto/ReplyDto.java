package com.ski.backend.web.dto;

import com.ski.backend.club.entity.ClubBoard;
import com.ski.backend.club.entity.Reply;
import com.ski.backend.domain.user.User;
import lombok.*;

import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReplyDto {

    // 댓글 식별값
    private Long id;

    // 동호회 게시판
    private long clubBoardId;


    // 댓글
    @NotBlank
    private String reply;

    // 작성시간
    private LocalDateTime createDt;

    // 수정시간
    private LocalDateTime updateDt;

    private String nickname;

    public ReplyDto(Reply reply, String nickname) {
        this.id = reply.getId();
        this.clubBoardId = reply.getClubBoard().getId();
        this.reply = reply.getReply();
        this.createDt = reply.getCreatedDate();
        this.updateDt = reply.getLastModifiedDate();
        this.nickname = nickname;
    }

    public Reply toEntity(User user, ClubBoard clubBoard) {
        return Reply.builder()
                .user(user)
                .clubBoard(clubBoard)
                .reply(reply)
                .build();
    }

}
