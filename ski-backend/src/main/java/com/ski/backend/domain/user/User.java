package com.ski.backend.domain.user;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.ski.backend.domain.Tayo.TayoUser;
import com.ski.backend.domain.board.Board;
import com.ski.backend.domain.board.Dislikes;
import com.ski.backend.domain.board.Likes;
import com.ski.backend.domain.carpool.Carpool;
import com.ski.backend.domain.club.Reply;
import com.ski.backend.domain.club.ClubUser;
import com.ski.backend.domain.club.Gender;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Cascade;

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
    private Long id;

    @Column(unique = true, nullable = false)
    private String username;
    @Column(unique = true)
    private String nickname;

    @Column(nullable = false)
    private String password;

    @Enumerated(EnumType.STRING)
    @Column
    private Gender gender;

    @Column
    private Integer age;

    @Column
    private Boolean agreement;

    @Enumerated(EnumType.STRING)
    @Column
    private Role roles;

    @JsonIgnoreProperties({"user"})
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Board> boards;

    @JsonIgnoreProperties({"user", "board"})
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Likes> likes;

    @JsonIgnoreProperties({"user", "board"})
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Dislikes> dislikes;

    @JsonIgnoreProperties({"user"})
    @OneToMany(cascade = CascadeType.ALL)
    private List<Whisper> whispers;

    @JsonIgnoreProperties({"user"})
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<ChatRoom> chatRooms;

    @JsonIgnoreProperties({"user"})
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<ClubUser> clubUsers = new ArrayList<>();

    @JsonIgnoreProperties({"user"})
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Carpool> carpools;

    @JsonIgnoreProperties({"user"})
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Reply> replies = new ArrayList<>();

    @JsonIgnoreProperties({"user", "tayo"})
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
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
