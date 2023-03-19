package com.ski.backend.domain.board;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.ski.backend.domain.user.User;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String content;

    @JoinColumn(name = "boardId")
    @ManyToOne
    private Board board;

    @JsonIgnoreProperties({"boards","clubUsers", "carpools", "tayos", "password"})
    @JoinColumn(name = "userId")
    @ManyToOne
    private User user;

    private LocalDateTime createDate;
    private LocalDateTime updateDate;

    @PrePersist
    public void createDate() {
        this.createDate = LocalDateTime.now();
    }

}
