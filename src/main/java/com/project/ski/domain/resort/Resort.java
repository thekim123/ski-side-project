package com.project.ski.domain.resort;


import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;


@Entity
@Data
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
