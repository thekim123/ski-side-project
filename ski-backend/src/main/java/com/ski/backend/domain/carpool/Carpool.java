package com.ski.backend.domain.carpool;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.ski.backend.domain.user.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class Carpool {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JsonIgnoreProperties({"boards", "clubUsers", "carpools", "tayoUsers", "password"})
    @JoinColumn(name = "userId")
    @ManyToOne
    private User user;

    @OneToOne(cascade = CascadeType.ALL)
    private Negotiate negotiate;

    private int curPassenger;
    private String departure;
    private String destination;
    private int passenger;
    private String memo;
    private LocalDateTime departTime;
    private boolean isSmoker;
    private String boarding;

    private String request;

    private LocalDateTime createDate;

    @PrePersist
    public void createDate() {
        this.createDate = LocalDateTime.now();
    }

}
