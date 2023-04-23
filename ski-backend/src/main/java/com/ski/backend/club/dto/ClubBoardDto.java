package com.ski.backend.club.dto;

import com.ski.backend.club.entity.ClubBoard;
import com.ski.backend.club.entity.ClubUser;

import com.ski.backend.web.dto.ReplyDto;
import lombok.*;


import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;


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
    private List<ReplyDto> replies;

    // 제목
    @NotBlank
    private String title;

    // 내용
    @NotBlank
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
        //this.replies =cb.getReplies().stream().map(ReplyDto::new).collect(Collectors.toList());
        this.replies = cb.getReplies().stream().map(r -> new ReplyDto(r, r.getUser().getNickname())).collect(Collectors.toList());
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
