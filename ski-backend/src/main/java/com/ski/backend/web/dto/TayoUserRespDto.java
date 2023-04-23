package com.ski.backend.web.dto;

import com.ski.backend.domain.Tayo.RideDevice;
import com.ski.backend.domain.Tayo.Tayo;
import com.ski.backend.domain.common.AgeGrp;
import com.ski.backend.domain.common.Status;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
public class TayoUserRespDto {

    private Long id;
    private Long resortId;

    private RideDevice rideDevice;

    private String title;

    private AgeGrp age;

    private int tayoMemCnt;

    private int curTayoMemCnt;

    // 홍보 문구
    private String comment;
    private long userId;

    private Status status;

    // 출발날짜
    private LocalDate tayoDt;

    // 시간

    private LocalDateTime tayoStrTime;

    private LocalDateTime tayoEndTime;

    private String userName;



    public TayoUserRespDto(Tayo tayo, long userId, String userName) {
        this.id = tayo.getId();
        this.resortId = tayo.getResort().getId();
        this.rideDevice = tayo.getRideDevice();
        this.title = tayo.getTitle();
        this.age = tayo.getAge();
        this.tayoMemCnt = tayo.getTayoMemCnt();
        this.curTayoMemCnt = tayo.getCurTayoMemCnt();
        this.comment = tayo.getComment();
        this.userId = userId;
        this.userName = userName;
        this.tayoDt = tayo.getTayoDt();
        this.tayoStrTime = tayo.getTayoStrTime();
        this.tayoEndTime = tayo.getTayoEndTime();
    }

    public TayoUserRespDto(Tayo tayo, Status status,String userName) {
        this.resortId =tayo.getResort().getId();
        this.rideDevice = tayo.getRideDevice();
        this.title = tayo.getTitle();
        this.age = tayo.getAge();
        this.tayoMemCnt = tayo.getTayoMemCnt();
        this.curTayoMemCnt = tayo.getCurTayoMemCnt();
        this.comment = tayo.getComment();
        this.tayoDt =tayo.getTayoDt();
        this.tayoStrTime = tayo.getTayoStrTime();
        this.tayoEndTime = tayo.getTayoEndTime();
        this.status = status;
        this.userName = userName;

    }





}
