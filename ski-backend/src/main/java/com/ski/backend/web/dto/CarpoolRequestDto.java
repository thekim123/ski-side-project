package com.ski.backend.web.dto;

import com.ski.backend.domain.carpool.Carpool;
import com.ski.backend.domain.carpool.Negotiate;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;

@NoArgsConstructor
@Data
public class CarpoolRequestDto {

    private Long id;
    @NotBlank
    private String departure;

    @NotBlank
    private String destination;

    @NotBlank
    private String boarding;

    @NotBlank
    private String space;
    private boolean smoker;

    @NotBlank
    private String phoneNumber;

    @NotBlank
    private int cost;
    private Negotiate negotiate;
    private int passenger;
    private String memo;

    @NotBlank
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
