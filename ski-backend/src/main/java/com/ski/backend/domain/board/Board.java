package com.ski.backend.domain.board;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.ski.backend.domain.resort.Resort;
import com.ski.backend.domain.user.User;
import com.ski.backend.web.dto.BoardDto;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Board {

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
    private List<Comment> comment;

    @JsonIgnoreProperties({"board"})
    @OneToMany(mappedBy = "board", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Likes> likes;

    @JsonIgnoreProperties({"board"})
    @OneToMany(mappedBy = "board", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Dislikes> dislikes;

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

    private LocalDateTime createDate;

    private long pageCount;

    @PrePersist
    public void createDate() {
        this.createDate = LocalDateTime.now();
    }


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
