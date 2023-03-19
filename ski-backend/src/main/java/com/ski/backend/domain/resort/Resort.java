package com.ski.backend.domain.resort;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;


@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Resort {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Enumerated(EnumType.STRING)
    @Column(length = 100, unique = true)
    private ResortName resortName;

    private LocalDateTime createDate;
    private LocalDateTime updateDate;

    @PrePersist
    public void createDate() {
        this.createDate = LocalDateTime.now();
    }
}
