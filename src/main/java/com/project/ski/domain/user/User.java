package com.project.ski.domain.user;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.project.ski.domain.board.Board;
import com.project.ski.domain.carpool.Carpool;
import com.project.ski.domain.club.Club;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.repository.Query;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static javax.persistence.FetchType.LAZY;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(unique = true, nullable = false)
    private String username;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String email;

    @Enumerated(EnumType.STRING)
    private Role roles;

    @JsonIgnoreProperties({"user"})
    @OneToMany(mappedBy = "user", fetch = LAZY)
    private List<Board> boards;

    @OneToMany(mappedBy = "user")
    private List<Club> clubs = new ArrayList<>();

    @OneToMany(mappedBy = "user", fetch = LAZY)
    private List<Carpool> carpools;

    private String profileImageUrl;

    private LocalDateTime createDate;

    private LocalDateTime updateDate;

    @PrePersist
    public void createDate() {
        this.createDate = LocalDateTime.now();
    }

}
