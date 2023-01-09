package com.ski.backend.web.dto;

import com.ski.backend.domain.carpool.Carpool;
import com.ski.backend.domain.carpool.Negotiate;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@NoArgsConstructor
@Data
public class CarpoolRequestDto {

    private Long id;
    private String departure;
    private String destination;
    private String boarding;
    private String space;
    private boolean smoker;
    private String phoneNumber;
    private int cost;
    private Negotiate negotiate;
    private int passenger;
    private String memo;
    private String request;
    private LocalDateTime departTime;

    public Carpool toEntity() {
        return Carpool.builder()
                .departTime(departTime)
                .departure(departure)
                .destination(destination)
                .passenger(passenger)
                .memo(memo)
                .boarding(boarding)
                .isSmoker(smoker)
                .negotiate(negotiate)
                .request(request)
                .build();
    }

}
