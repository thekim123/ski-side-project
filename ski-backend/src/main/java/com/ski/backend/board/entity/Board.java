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


    /**
     * @apiNote dto -> entity 매핑 메서드이다. 이걸 왜 entity layer 에 두었는지에 대한 근거는 아래에 서술한다.<br/>
     * 1. Cohesion(응집도) 증가: Entity 클래스 내에서 해당 Entity 객체와 관련된 변환 로직을 함께 두면,
     * 관련된 기능이 한 곳에 모여있어 코드의 응집도가 높아진다.
     * 이는 코드의 가독성과 유지보수성을 높이는데 도움이 된다.<br/>
     * 2. 변환 로직의 중앙화: DTO 를 Entity 로 변환하는 로직을 Entity 클래스에 두면,
     * 변환 로직이 중앙화되어 관리하기 쉬워진다. 이로 인해 코드의 일관성이 유지되며,
     * 변환 로직에 대한 변경이 필요한 경우 한 곳에서만 수정하면 된다.<br/>
     * @author thekim123
     */
    public void update(BoardDto.Save dto, Resort resort) {
        this.content = dto.getContent();
        this.title = dto.getTitle();
        this.resort = resort;
    }


    // 좋아요와 싫어요를 주입하는 비즈니스 로직
    // TODO: 비즈니스 로직을 Service 레이어에 두도록 하자.
    // 또는
    // 좋아요와 싫어요 정보를 담고 있는 VO를 정의한다.
    // 해당 VO를 Service 레이어에서 사용하여 게시글의 좋아요와 싫어요 정보를 처리하고 저장하는 로직을 구현한다.
    // 필요한 경우, VO를 사용하여 다른 계층과의 데이터 전달을 수행한다.
    // 둘 중 하나로 수정하도록 해보자.
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
