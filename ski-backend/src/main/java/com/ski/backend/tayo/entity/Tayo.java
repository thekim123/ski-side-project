package com.ski.backend.tayo.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.ski.backend.common.BaseTimeEntity;
import com.ski.backend.user.entity.AgeGrp;
import com.ski.backend.resort.entity.Resort;
import com.ski.backend.tayo.dto.TayoRequestDto;
import lombok.*;

import javax.persistence.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static javax.persistence.EnumType.STRING;
import static javax.persistence.FetchType.EAGER;
import static javax.persistence.FetchType.LAZY;

@Getter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@ToString
public class Tayo extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "tayo_id")
    private long id;

    @JsonIgnore
    @OneToOne(fetch = LAZY)
    @JoinColumn(name = "resort_id")
    private Resort resort;

    @JsonIgnoreProperties({"tayo"})
    @OneToMany(mappedBy = "tayo", fetch = EAGER, orphanRemoval = true)
    @Builder.Default
    private List<TayoUser> tayoUsers = new ArrayList<>();

    // 보드 / 스키
    @Enumerated(EnumType.STRING)
    private RideDevice rideDevice;

    // 제목
    @Column(nullable = false)
    private String title;

    // 나이
    @Column(nullable = false)
    @Enumerated(STRING)
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
        this.tayoDt = dto.getTayoDt();
        this.tayoStrTime = dto.getTayoStrTime();
        this.tayoEndTime = dto.getTayoEndTime();
    }

    public void addMember() {
        this.curTayoMemCnt++;
    }


}
