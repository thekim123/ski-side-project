package com.project.ski.domain.Tayo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.project.ski.domain.BaseTimeEntity;
import com.project.ski.domain.club.AgeGrp;
import com.project.ski.domain.resort.Resort;
import com.project.ski.domain.user.User;
import com.project.ski.web.dto.TayoRequestDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import javax.persistence.*;

import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

import static javax.persistence.FetchType.LAZY;

@Getter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Tayo extends BaseTimeEntity {

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

    // 보드 / 스키
    @Column(nullable = false)
    private String rideDevice;

    // 제목
    @Column(nullable = false)
    private String title;

    // 나이
    @Column(nullable = false)
    private AgeGrp age;

    // 모집 인원 수
    private int tayoMemCnt;

    // 현재 인원 수
    private int curTayoMemCnt;

    // 홍보 문구
    private String comment;

    // 출발날짜
    private LocalDate tayoDt;

    // 시간
    private LocalDateTime tayoStrTime;

    private LocalDateTime tayoEndTime;


    public void update(TayoRequestDto dto) {
        this.title = dto.getTitle();
        this.age = dto.getAge();
        this.tayoMemCnt = dto.getTayoMemCnt();
    }



}
