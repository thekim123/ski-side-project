package com.ski.backend.board.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.ski.backend.common.BaseTimeEntity;
import com.ski.backend.resort.entity.Resort;
import com.ski.backend.user.entity.User;
import com.ski.backend.board.dto.BoardDto;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@ToString
public class Board extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;
    private String content;
    private String postImageUrl;

    @JoinColumn(name = "resortId")
    @ManyToOne
    private Resort resort;

    @JsonIgnoreProperties({"boards", "password", "clubUsers", "tayoUsers"})
    @JoinColumn(name = "userId")
    @ManyToOne(fetch = FetchType.EAGER)
    private User user;

    @JsonIgnoreProperties("board")
    @OneToMany(mappedBy = "board", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @Builder.Default
    private List<Comment> comment = new ArrayList<>();

    @JsonIgnoreProperties({"board"})
    @OneToMany(mappedBy = "board", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @Builder.Default
    private List<Likes> likes = new ArrayList<>();

    @JsonIgnoreProperties({"board"})
    @OneToMany(mappedBy = "board", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @Builder.Default
    private List<Dislikes> dislikes = new ArrayList<>();

    @Transient
    private long totalLikeCount;

    @Transient
    private long likeCount;

    @Transient
    private boolean likeState;

    @Transient
    private long dislikeCount;

    @Transient
    private boolean dislikeState;

    private long pageCount;


    public void changeData(BoardDto.Save dto, Resort resort) {
        this.content = dto.getContent();
        this.title = dto.getTitle();
        this.resort = resort;
    }

    // user 의 setter
    public void withUserAndResort(User user, Resort resort) {
        this.user = user;
        this.resort = resort;
    }


    // 좋아요와 싫어요를 주입하는 비즈니스 로직
    public void loadLikesAndDislikes(long principalId) {

        long likeCount = this.getLikes().size();
        long dislikeCount = this.getDislikes().size();

        this.likeCount = likeCount;
        this.dislikeCount = dislikeCount;
        this.totalLikeCount = likeCount - dislikeCount;

        this.getLikes().forEach((like) -> {
            if (like.getUser().getId() == principalId) {
                this.likeState = true;
            }
        });

        this.getDislikes().forEach(dislikes -> {
            if (dislikes.getUser().getId() == principalId) {
                this.dislikeState = true;
            }
        });
    }

}
