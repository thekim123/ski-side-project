package com.project.ski.web.dto;

import com.project.ski.domain.club.Club;

import com.project.ski.domain.club.ClubBoard;
import com.project.ski.domain.club.Reply;

import com.project.ski.domain.user.User;
import lombok.*;


import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;



@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor

public class ClubBoardDto {

    // 동호회 게시판 식별값
    private Long id;

    // 동호회

    private Long clubId;

    // 댓글
    private List<Reply> replies;

    // 제목
    private String title;

    // 내용
    private String content;

    /**
     * 임시저장 여부
     * default - N
     */

    private String tempFlag;

    /**
     * 공지 / 일반 분류
     * ㄴ sort : notice  ; 공지
     * ㄴ sort : general  : 일반
     */

    private String sortScope;

    private String nickName;

    private LocalDateTime createDt;

    private LocalDateTime updateDt;

    public ClubBoardDto(ClubBoard clubBoard) {
        this.id = clubBoard.getId();
        this.clubId =clubBoard.getClub().getId();
        this.replies =clubBoard.getReplies();
        this.title =clubBoard.getTitle();
        this.content =clubBoard.getContent();
        this.tempFlag =clubBoard.getTempFlag();
        this.sortScope =clubBoard.getSortScope();
        this.createDt = clubBoard.getCreatedDate();
    }
    public ClubBoardDto(ClubBoard clubBoard ,User user) {
        this.id = clubBoard.getId();
        this.clubId =clubBoard.getClub().getId();
        this.replies =clubBoard.getReplies();
        this.title =clubBoard.getTitle();
        this.content =clubBoard.getContent();
        this.tempFlag =clubBoard.getTempFlag();
        this.sortScope =clubBoard.getSortScope();
        this.nickName = user.getNickname();
        this.createDt = clubBoard.getCreatedDate();
    }


    public ClubBoard toEntity(User user,Club club) {
        return ClubBoard.builder()
                .club(club)
                .title(title)
                .content(content)
                .tempFlag(tempFlag)
                .sortScope(sortScope)
                .build();
    }


}
