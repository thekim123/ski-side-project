package com.ski.backend.web.dto;


import com.ski.backend.domain.Tayo.RideDevice;
import com.ski.backend.domain.Tayo.Tayo;
import com.ski.backend.domain.common.AgeGrp;
import com.ski.backend.domain.resort.Resort;
import com.ski.backend.domain.user.User;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Getter
@NoArgsConstructor
public class TayoRequestDto {

    // 타요 식별 id
    private Long id;

    private Long resortId;

    private RideDevice rideDevice;
    private String title;

    private AgeGrp age;

    private int tayoMemCnt;

    // 홍보 문구
    private String comment;

    // 출발날짜
    private LocalDate tayoDt;

    // 시간
    private LocalDateTime tayoStrTime;

    private LocalDateTime tayoEndTime;


    public TayoRequestDto tayoDto(Tayo tayo) {
        this.id = tayo.getId();
        this.resortId = tayo.getResort().getId();
        this.rideDevice = tayo.getRideDevice();
        this.title = tayo.getTitle();
        this.age =tayo.getAge();
        this.tayoMemCnt = tayo.getTayoMemCnt();
        this.comment = tayo.getComment();
        this.tayoDt = tayo.getTayoDt();
        this.tayoStrTime = tayo.getTayoStrTime();
        this.tayoEndTime = tayo.getTayoEndTime();
        return this;
    }

    public Tayo toEntity(User user, Resort resort) {
        return Tayo.builder()
                .title(title)
                .rideDevice(rideDevice)
                .resort(resort)
                .age(age)
                .tayoMemCnt(tayoMemCnt)
                .curTayoMemCnt(+1)
                .tayoDt(tayoDt)
                .tayoStrTime(tayoStrTime)
                .tayoEndTime(tayoEndTime)
                .comment(comment)
                .build();
    }

}
