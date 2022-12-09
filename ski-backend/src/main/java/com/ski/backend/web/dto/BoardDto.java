package com.ski.backend.web.dto;

import com.ski.backend.domain.board.Board;
import com.ski.backend.domain.resort.Resort;
import com.ski.backend.domain.user.User;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@NoArgsConstructor
@Data
public class BoardDto {

    private Long id;
    private String title;
    private String content;
    private String username;
    private String nickname;
    private String resortName;
    private LocalDateTime createDate;

    public Board toEntity(User user, Resort resort) {
        return Board.builder()
                .user(user)
                .resort(resort)
                .content(content)
                .title(title)
                .build();
    }
}
