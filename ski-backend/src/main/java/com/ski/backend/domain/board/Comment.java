package com.ski.backend.domain.board;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.ski.backend.domain.BaseTimeEntity;
import com.ski.backend.domain.user.User;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(callSuper = true)
@ToString
public class Comment extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String content;

    @JoinColumn(name = "boardId")
    @ManyToOne
    private Board board;

    @JsonIgnoreProperties({"boards", "clubUsers", "carpools", "tayos", "password"})
    @JoinColumn(name = "userId")
    @ManyToOne
    private User user;

}
