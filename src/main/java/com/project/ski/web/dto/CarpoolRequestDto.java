package com.project.ski.web.dto;

import com.project.ski.domain.carpool.Carpool;
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
    private String smoke;
    private String phoneNumber;
    private int cost;
    private int passenger;
    private String memo;
    private LocalDateTime departTime;

    public Carpool toEntity() {
        return Carpool.builder()
                .id(id)
                .departTime(departTime)
                .departure(departure)
                .destination(destination)
                .passenger(passenger)
                .memo(memo)
                .build();
    }

}
