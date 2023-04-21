package com.ski.backend.domain.club;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.ski.backend.domain.BaseTimeEntity;
import com.ski.backend.domain.user.User;
import com.ski.backend.web.dto.ClubBoardDto;
import lombok.*;

import javax.persistence.*;

import java.util.ArrayList;
import java.util.List;

import static javax.persistence.CascadeType.ALL;
import static javax.persistence.CascadeType.REMOVE;
import static javax.persistence.FetchType.LAZY;

@Entity
@Getter
@NoArgsConstructor
@Builder
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@ToString
public class ClubBoard extends BaseTimeEntity {

    // 동호회 게시판 식별값
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "clubBoard_id")
    private Long id;

    @JoinColumn(name = "clubUser_id")
    @ManyToOne(fetch = LAZY)
    private ClubUser clubUser;

    // 댓글
    @JsonIgnore
    @OneToMany(mappedBy = "clubBoard", cascade = ALL, orphanRemoval = true)
    @Builder.Default
    private List<Reply> replies = new ArrayList<>();

    // 제목
    @Column(nullable = false)
    private String title;

    // 내용
    @Column(nullable = false)
    private String content;

    /**
     * 임시저장 여부
     * default - N
     */
    @Column(nullable = false)
    private String tempFlag;

    /**
     * 공지 / 일반 분류
     * ㄴ sort : notice  : 공지
     * ㄴ sort : general  : 일반
     */
    @Column(nullable = false)
    private String sortScope;


    public void update(ClubBoardDto cb) {
        this.title = cb.getTitle();
        this.content = cb.getContent();
        this.tempFlag = cb.getTempFlag();
        this.sortScope = cb.getSortScope();
    }
}
