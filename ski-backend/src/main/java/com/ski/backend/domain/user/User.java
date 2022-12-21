package com.ski.backend.domain.user;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.ski.backend.config.auth.PrincipalDetails;
import com.ski.backend.domain.Tayo.Tayo;
import com.ski.backend.domain.Tayo.TayoUser;
import com.ski.backend.domain.board.Board;
import com.ski.backend.domain.carpool.Carpool;
import com.ski.backend.domain.common.AgeGrp;
import com.ski.backend.domain.club.ClubUser;
import com.ski.backend.domain.club.Gender;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.Authentication;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(unique = true, nullable = false)
    private String username;
    @Column(unique = true, nullable = false)
    private String nickname;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String email;

    @Enumerated(EnumType.STRING)
    @Column(nullable = true)
    private Gender gender;

    @Enumerated(EnumType.STRING)
    @Column(nullable = true)
    private AgeGrp ageGrp;

    @Enumerated(EnumType.STRING)
    private Role roles;

    @JsonIgnoreProperties({"user"})
    @OneToMany(mappedBy = "user")
    private List<Board> boards;

    @JsonIgnoreProperties({"user"})
    @OneToMany
    private List<ChatRoom> chatRooms;

    @JsonIgnoreProperties({"user"})
    @OneToMany(mappedBy = "user")
    private List<ClubUser> clubUsers = new ArrayList<>();

    @OneToMany(mappedBy = "user", fetch = LAZY)
    private List<Carpool> carpools;

    @JsonIgnoreProperties({"user"})
    @OneToMany(mappedBy = "user")
    private List<TayoUser> tayoUsers = new ArrayList<>();

    private LocalDateTime createDate;

    private LocalDateTime updateDate;

    @PrePersist
    public void createDate() {
        this.createDate = LocalDateTime.now();
    }

//    public void removeClub(ClubUser clubUser) {
//        clubUsers.remove(clubUser);
//    }
}
