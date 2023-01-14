package com.ski.backend.web.dto;

import com.ski.backend.domain.Tayo.RideDevice;
import com.ski.backend.domain.Tayo.Tayo;
import com.ski.backend.domain.Tayo.TayoUser;
import com.ski.backend.domain.common.AgeGrp;
import com.ski.backend.domain.user.User;
import lombok.Data;
import org.springframework.data.domain.Page;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Data
public class TayoRespDto {

    // 타요 식별 id
    private Long id;

    private Long resortId;

    private Long userId;
    private RideDevice rideDevice;

    private String title;

    private AgeGrp age;

    private int tayoMemCnt;

    private int curTayoMemCnt;

    // 홍보 문구
    private String comment;

    // 출발날짜
    private LocalDate tayoDt;

    // 시간
    private LocalDateTime tayoStrTime;

    private LocalDateTime tayoEndTime;


    public TayoRespDto(Long id,Long resortId, RideDevice rideDevice, String title, int tayoMemCnt, int curTayoMemCnt, AgeGrp age, LocalDate tayoDt, LocalDateTime tayoStrTime, LocalDateTime tayoEndTime) {
        this.id = id;
        this.resortId = resortId;
        this.rideDevice = rideDevice;
        this.title = title;
        this.tayoMemCnt = tayoMemCnt;
        this.curTayoMemCnt = curTayoMemCnt;
        this.age = age;
        this.tayoDt = tayoDt;
        this.tayoStrTime = tayoStrTime;
        this.tayoEndTime = tayoEndTime;
    }

    public TayoRespDto(Tayo tu, User user) {
        this.resortId = tu.getResort().getId();
        this.rideDevice = tu.getRideDevice();
        this.title = tu.getTitle();
        this.age = tu.getAge();
        this.tayoMemCnt = tu.getTayoMemCnt();
        this.curTayoMemCnt = tu.getCurTayoMemCnt();
        this.comment = tu.getComment();
        this.tayoDt = tu.getTayoDt();;
        this.tayoStrTime = tu.getTayoStrTime();
        this.tayoEndTime = tu.getTayoEndTime();
        this.userId = user.getId();
    }


}
