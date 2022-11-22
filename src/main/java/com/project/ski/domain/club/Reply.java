package com.project.ski.domain.club;

import com.project.ski.domain.user.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;

import static javax.persistence.FetchType.LAZY;

@Getter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor

public class Reply {

    // 댓글 식별값
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // 동호회 게시판
    @JoinColumn(name = "clubBoard_Id")
    @ManyToOne(fetch = LAZY)
    private ClubBoard clubBoard;

    // 사용자
    @JoinColumn(name = "user_Id")
    @ManyToOne
    private User user;

    // 댓글
    private String reply;

    // 작성시간
    @CreationTimestamp
    private LocalDateTime createDt;

    // 수정시간
    @CreationTimestamp
    private LocalDateTime updateDt;

    public void save(ClubBoard clubBoard, User user) {
        this.clubBoard = clubBoard;
        this.user = user;
    }

}
