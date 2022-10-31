package com.project.ski.domain.carpool;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.project.ski.domain.user.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

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

    @JsonIgnoreProperties({"carpools"})
    @JoinColumn(name="userId")
    @ManyToOne
    private User user;

    private String departure;
    private String destination;
    private String boarding;
    private String space;
    private String smoke;
    private String phoneNumber;
    private int cost;
    private int passenger;
    private String memo;
    private LocalDateTime departTime;

    private LocalDateTime createDate;

    @PrePersist
    public void createDate() {
        this.createDate = LocalDateTime.now();
    }

}
