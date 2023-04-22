package com.ski.backend.domain.user;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.ski.backend.domain.BaseTimeEntity;
import com.ski.backend.domain.Tayo.TayoUser;
import com.ski.backend.domain.board.Board;
import com.ski.backend.domain.board.Bookmark;
import com.ski.backend.domain.board.Dislikes;
import com.ski.backend.domain.board.Likes;
import com.ski.backend.domain.carpool.Carpool;
import com.ski.backend.domain.club.Reply;
import com.ski.backend.domain.club.ClubUser;
import com.ski.backend.domain.club.Gender;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;


@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@ToString
public class User extends BaseTimeEntity {

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
    @Builder.Default
    private List<Board> boards = new ArrayList<>();

    @JsonIgnoreProperties({"user", "board"})
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    @Builder.Default
    private List<Likes> likes = new ArrayList<>();

    @JsonIgnoreProperties({"user", "board"})
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    @Builder.Default
    private List<Dislikes> dislikes = new ArrayList<>();

    @JsonIgnoreProperties({"fromUser"})
    @OneToMany(mappedBy = "fromUser", cascade = CascadeType.ALL)
    @Builder.Default
    private List<Bookmark> bookmarks = new ArrayList<>();

    @JsonIgnoreProperties({"user"})
    @OneToMany(cascade = CascadeType.ALL)
    @Builder.Default
    private List<Whisper> whispers = new ArrayList<>();

    @JsonIgnoreProperties({"user"})
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    @Builder.Default
    private List<ChatRoom> chatRooms = new ArrayList<>();

    @JsonIgnoreProperties({"user"})
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    @Builder.Default
    private List<ClubUser> clubUsers = new ArrayList<>();

    @JsonIgnoreProperties({"writer"})
    @OneToMany(mappedBy = "writer", cascade = CascadeType.ALL)
    @Builder.Default
    private List<Carpool> carpools = new ArrayList<>();

    @JsonIgnoreProperties({"user"})
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    @Builder.Default
    private List<Reply> replies = new ArrayList<>();

    @JsonIgnoreProperties({"user", "tayo"})
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    @Builder.Default
    private List<TayoUser> tayoUsers = new ArrayList<>();

//    public void removeClub(ClubUser clubUser) {
//        clubUsers.remove(clubUser);
//    }
}
