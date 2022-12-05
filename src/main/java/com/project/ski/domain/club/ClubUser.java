package com.project.ski.domain.club;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.project.ski.domain.user.User;
import com.project.ski.web.dto.ClubBoardDto;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

import static javax.persistence.FetchType.LAZY;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class ClubUser {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @ManyToOne(fetch = LAZY)  // 개인이 여러개의 클럽에 속할 수 있으니까
    @JoinColumn(name = "club_id")
    private Club club;

    @JsonIgnoreProperties({"board", "clubUsers"})
    @ManyToOne(fetch = LAZY)// 여러 사용자가 한 클럽에 속할 수 있으니까
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = LAZY)// 여러 사용자가 한 클럽에 속할 수 있으니까
    @JoinColumn(name = "clubBoard_id")
    private ClubBoard clubBoard;

    /**
     * 방장만이 권할을 부여 할 수 있음
     *
     * 권한
     * 관리자
     *
     */
    private String role;
    public ClubUser(Club club, User user) {
        this.club = club;
        this.user = user;
    }

    public ClubUser(ClubBoard clubBoard, User user) {
        this.clubBoard = clubBoard;
        this.user = user;
    }
}
