package com.project.ski.domain.club;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

import java.util.ArrayList;
import java.util.List;

import static javax.persistence.FetchType.LAZY;

@Entity
@Getter
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class ClubBoard {

    // 동호회 게시판 식별값
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "clubBoard_id")
    private Long id;

    // 동호회
    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "club_id")
    private Club club;

    // 댓글
    @OneToMany(mappedBy = "clubBoard")
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
     * ㄴ sort : notice  ; 공지
     * ㄴ sort : general  : 일반
     */
    @Column(nullable = false)
    private String sortScope;

    /**
     * 방장만이 권할을 부여 할 수 있음
     *
     * 권한
     * 관리자
     *
     */
    private String role;

}
