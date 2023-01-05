package com.ski.backend.domain.club;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.ski.backend.domain.BaseTimeEntity;
import com.ski.backend.domain.user.User;
import com.ski.backend.web.dto.ReplyDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

import static javax.persistence.FetchType.LAZY;

@Getter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor

public class Reply extends BaseTimeEntity {

    // 댓글 식별값
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // 동호회 게시판
    @JsonIgnore
    @JoinColumn(name = "clubBoard_Id")
    @ManyToOne(fetch = LAZY)
    private ClubBoard clubBoard;

    // 사용자
    @JsonIgnoreProperties({"boards","clubUsers", "carpools", "tayoUsers", "password"})
    @JoinColumn(name = "user_Id")
    @ManyToOne
    private User user;

    // 댓글
    private String reply;

    public ReplyDto update(ReplyDto dto) {
        this.reply = dto.getReply();
        return dto;
    }

}
