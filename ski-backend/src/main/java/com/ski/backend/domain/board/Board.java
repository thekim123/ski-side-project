package com.ski.backend.domain.board;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.ski.backend.domain.resort.Resort;
import com.ski.backend.domain.user.User;
import com.ski.backend.web.dto.BoardDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Data
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

    @JoinColumn(name = "resortId")
    @ManyToOne
    private Resort resort;

    @JsonIgnoreProperties({"boards", "password", "clubUsers"})
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


    public void changeData(BoardDto dto, Resort resort) {
        this.setContent(dto.getContent());
        this.setTitle(dto.getTitle());
        this.setResort(resort);
    }

    public void loadLikesAndDislikes(long principalId) {

        long likeCount = this.getLikes().size();
        long dislikeCount = this.getDislikes().size();

        this.setLikeCount(likeCount);
        this.setDislikeCount(dislikeCount);
        this.setTotalLikeCount(likeCount - dislikeCount);

        this.getLikes().forEach((like) -> {
            if (like.getUser().getId() == principalId) {
                this.setLikeState(true);
            }
        });

        this.getDislikes().forEach(dislikes -> {
            if (dislikes.getUser().getId() == principalId) {
                this.setDislikeState(true);
            }
        });
    }

}
