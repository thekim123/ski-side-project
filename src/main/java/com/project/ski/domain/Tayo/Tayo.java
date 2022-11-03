package com.project.ski.domain.Tayo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.project.ski.domain.resort.Resort;
import com.project.ski.domain.user.User;
import com.project.ski.web.dto.TayoRequestDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;

import java.time.LocalDateTime;

import static javax.persistence.FetchType.LAZY;

@Getter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Tayo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "tayo_id")
    private long id;

    @JsonIgnore
    @JoinColumn(name="user_id")
    @ManyToOne
    private User user;

    @JsonIgnore
    @OneToOne(fetch = LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "resort_id")
    private Resort resort;

    private String title;

    private String age;

    private int tayoMemCnt;

    @CreationTimestamp
    private LocalDateTime createDt;

    public void update(TayoRequestDto dto) {
        this.title = dto.getTitle();
        this.age = dto.getAge();
        this.tayoMemCnt = dto.getTayoMemCnt();
    }



}
