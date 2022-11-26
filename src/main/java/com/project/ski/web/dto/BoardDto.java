package com.project.ski.web.dto;

import com.project.ski.domain.board.Board;
import com.project.ski.domain.resort.Resort;
import com.project.ski.domain.user.User;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

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
