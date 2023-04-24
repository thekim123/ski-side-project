package com.ski.backend.user.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.ski.backend.common.BaseTimeEntity;
import com.ski.backend.tayo.entity.TayoUser;
import com.ski.backend.board.entity.Board;
import com.ski.backend.board.entity.Bookmark;
import com.ski.backend.board.entity.Dislikes;
import com.ski.backend.board.entity.Likes;
import com.ski.backend.carpool.entity.Carpool;
import com.ski.backend.club.entity.Reply;
import com.ski.backend.club.entity.ClubUser;
import com.ski.backend.club.entity.Gender;
import com.ski.backend.user.vo.UserVo;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Email;
import java.util.ArrayList;
import java.util.List;


@Getter
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

    @Column(unique = true, nullable = false)
    @Email
    private String email;

    @Column(nullable = false)
    private String password;

    @Enumerated(EnumType.STRING)
    @Column
    private Gender gender;

    @Column
    private Integer age;

    @Column
    @Builder.Default
    private Boolean agreement = false;

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

    public void updateUser(UserVo vo) {
        this.agreement = vo.getAgreement();
        this.password = vo.getPassword();
        this.age = vo.getAge();
        this.nickname = vo.getNickname();
        this.roles = vo.getRole();
        this.gender = vo.getGender();
        this.username = vo.getUsername();
        this.email = vo.getEmail();
    }

}
