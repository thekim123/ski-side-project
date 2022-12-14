package com.ski.backend.domain.club;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.ski.backend.domain.common.Status;
import com.ski.backend.domain.user.User;
import lombok.*;

import javax.persistence.*;

import java.util.ArrayList;
import java.util.List;

import static javax.persistence.CascadeType.ALL;
import static javax.persistence.FetchType.LAZY;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Builder
@AllArgsConstructor
@Table(name = "club_users")
public class ClubUser {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = LAZY)  // 개인이 여러개의 클럽에 속할 수 있으니까
    @JoinColumn(name = "club_id")
    private Club club;

    @JsonIgnoreProperties({"board"})
    @ManyToOne(fetch = LAZY)// 여러 사용자가 한 클럽에 속할 수 있으니까
    @JoinColumn(name = "user_id")
    private User user;


    @OneToMany( mappedBy = "clubUser",cascade = ALL, orphanRemoval = true)
    private List<ClubBoard> clubBoards = new ArrayList<>();
    // 승인여부 확인용

    @Enumerated(EnumType.STRING)
    private Status status;

    /**
     * 방장만이 권한을 부여 할 수 있음
     *
     * 권한
     * 관리자
     */
    private String role;


    public ClubUser(Club club, User user,Status status, String role) {
        this.club = club;
        this.user = user;
        this.status = status;
        this.role = role;
    }

    public ClubUser(Club club, User user) {
        if (club.getOpenYn().equals("Y")) {
            // 오픈방
            this.status = Status.ADMIT;

        } else {
            // 비밀방
            this.status = Status.WAITING;
        }
        this.role = "회원";
        this.club = club;
        this.user = user;
    }

    public void update() {
        this.status = Status.ADMIT;
    }

    public void decline() {
        this.status = Status.DENIED;
    }
}
