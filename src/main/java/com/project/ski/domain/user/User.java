package com.project.ski.domain.user;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.project.ski.domain.Tayo.Tayo;
import com.project.ski.domain.board.Board;
import com.project.ski.domain.carpool.Carpool;
import com.project.ski.domain.club.Club;
import com.project.ski.domain.club.ClubUser;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.repository.Query;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static javax.persistence.FetchType.LAZY;

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
    private Role roles;

    @JsonIgnoreProperties({"user"})
    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    private List<Board> boards;


    @OneToMany(mappedBy = "user",fetch = LAZY, cascade = CascadeType.ALL)
    private List<ClubUser> clubUsers = new ArrayList<>();

    @OneToMany(mappedBy = "user", fetch = LAZY)
    private List<Carpool> carpools;

    @JsonIgnoreProperties({"user"})
    @OneToMany(mappedBy = "user")
    private List<Tayo> tayos = new ArrayList<>();

    private String profileImageUrl;

    private LocalDateTime createDate;

    private LocalDateTime updateDate;

    @PrePersist
    public void createDate() {
        this.createDate = LocalDateTime.now();
    }

    public void removeClub(ClubUser clubUser) {
        clubUsers.remove(clubUser);

    }
}
