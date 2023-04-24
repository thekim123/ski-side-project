package com.ski.backend.board.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.ski.backend.common.BaseTimeEntity;
import com.ski.backend.user.entity.User;
import lombok.*;

import javax.persistence.*;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@EqualsAndHashCode(callSuper = true)
@ToString
@Table(
        name = "likes",
        uniqueConstraints = {
                @UniqueConstraint(
                        name = "likes_uk",
                        columnNames = {"boardId", "userId"}
                )
        }
)
public class Likes extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "boardId")
    private Board board;

    @ManyToOne
    @JoinColumn(name = "userId")
    @JsonIgnoreProperties({"boards", "password", "clubUsers", "carpools", "tayos", "likes", "dislikes"})
    private User user;

}
