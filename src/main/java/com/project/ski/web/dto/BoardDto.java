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
    private MultipartFile file;
    private String username;
    private String nickname;
    private String resortName;
    private LocalDateTime createDate;

    public BoardDto toDto(Board board, String postImageUrl) {
        this.id = board.getId();
        this.title = board.getTitle();
        this.content = board.getContent();
        this.username = board.getUser().getUsername();
        this.nickname = board.getUser().getNickname();
        this.createDate = board.getCreateDate();
        return this;
    }

    //    public Board toEntity(String postImageUrl, User user) {
    public Board toEntity(User user, Resort resort, String postImageUrl) {

        return Board.builder()
                .user(user)
                .postImageUrl(postImageUrl)
                .resort(resort)
                .content(content)
                .title(title)
                .build();
    }

    public Board toEntityIfImageNull(User user, Resort resort) {
        return Board.builder()
                .user(user)
                //.postImageUrl(postImageUrl)
                .resort(resort)
                .content(content)
                .title(title)
                .build();
    }
}
