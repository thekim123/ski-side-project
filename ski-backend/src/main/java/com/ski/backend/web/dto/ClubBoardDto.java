package com.ski.backend.web.dto;

import com.ski.backend.domain.club.Club;

import com.ski.backend.domain.club.ClubBoard;
import com.ski.backend.domain.club.ClubUser;
import com.ski.backend.domain.club.Reply;

import com.ski.backend.domain.user.User;
import lombok.*;


import java.time.LocalDateTime;
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

    // 유저
    private Long userId;

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


    public ClubBoardDto(ClubBoard cb) {
        this.id = cb.getId();
        this.replies =cb.getReplies();
        this.title =cb.getTitle();
        this.content =cb.getContent();
        this.tempFlag =cb.getTempFlag();
        this.sortScope =cb.getSortScope();
        this.nickName = cb.getClubUser().getUser().getNickname();
        this.createDt = cb.getCreatedDate();
    }


    public ClubBoard toEntity(ClubUser clubUser) {
        return ClubBoard.builder()
                .clubUser(clubUser)
                .title(title)
                .content(content)
                .tempFlag(tempFlag)
                .sortScope(sortScope)
                .build();
    }


}
