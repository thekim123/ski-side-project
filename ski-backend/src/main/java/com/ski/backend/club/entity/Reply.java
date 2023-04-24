package com.ski.backend.club.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.ski.backend.common.BaseTimeEntity;
import com.ski.backend.user.entity.User;
import com.ski.backend.club.dto.ReplyDto;
import lombok.*;

import javax.persistence.*;

import static javax.persistence.FetchType.LAZY;

@Getter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@ToString
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
