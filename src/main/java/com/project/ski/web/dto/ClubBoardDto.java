package com.project.ski.web.dto;

import com.project.ski.domain.club.Club;

import com.project.ski.domain.club.ClubBoard;
import com.project.ski.domain.club.Reply;

import com.project.ski.domain.user.User;
import lombok.*;


import java.util.ArrayList;
import java.util.List;



@Data
public class ClubBoardDto {

    // 동호회 게시판 식별값
    private Long id;

    // 동호회

    private Long clubId;

    // 댓글
    private List<Reply> replies = new ArrayList<>();

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

    /**
     * 방장만이 권할을 부여 할 수 있음
     *
     * 권한
     * 관리자
     *
     */
    private String role;


    public ClubBoardDto(Long id, Long clubId, String title, String content, String tempFlag, String sortScope, String role) {
        this.id = id;
        this.clubId = clubId;
        this.title = title;
        this.content = content;
        this.tempFlag = tempFlag;
        this.sortScope = sortScope;
        this.role = role;
    }

    public ClubBoardDto(Long id) {
        this.id = id;
    }

    public ClubBoard toEntity(User user,Club club) {
        return ClubBoard.builder()
                .club(club)
                .title(title)
                .content(content)
                .tempFlag(tempFlag)
                .sortScope(sortScope)
                .role(role)
                .build();
    }
}
