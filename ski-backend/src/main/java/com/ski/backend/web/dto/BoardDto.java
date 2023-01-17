package com.ski.backend.web.dto;

import com.ski.backend.domain.board.Board;
import com.ski.backend.domain.resort.Resort;
import com.ski.backend.domain.user.User;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;

@NoArgsConstructor
@Data
public class BoardDto {

    private Long id;
    @NotBlank
    private String title;

    @NotBlank
    private String content;
    private String username;
    private String nickname;

    @NotBlank
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
