package com.project.ski.web.dto;

import com.project.ski.domain.board.Board;
import com.project.ski.domain.resort.ResortName;
import com.project.ski.domain.user.User;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;

@NoArgsConstructor
@Data
public class BoardRequestDto {

    private Long id;
    private String title;
    private String content;
    private MultipartFile file;
    private String username;
    private String resortName;
    private LocalDateTime createDate;

    public BoardRequestDto toDto(Board board, String postImageUrl) {
        this.id = board.getId();
        this.title = board.getTitle();
        this.content = board.getContent();
        this.username = board.getUser().getUsername();
        this.createDate = board.getCreateDate();
        this.resortName = board.getResortName().toString();
        return this;
    }

    public Board toEntity(String postImageUrl, User user) {
        return Board.builder()
                .user(user)
                .postImageUrl(postImageUrl)
                .content(content)
                .resortName(ResortName.valueOf(resortName))
                .title(title)
                .build();
    }
}
