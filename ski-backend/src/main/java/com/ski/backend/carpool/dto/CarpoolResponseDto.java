package com.ski.backend.carpool.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;

@NoArgsConstructor
@Getter
@Builder
@AllArgsConstructor
public class CarpoolResponseDto {

    private Long id;
    @NotBlank
    private String departure;

    @NotBlank
    private String destination;

    @NotBlank
    private String boarding;

    private boolean smoker;

    private NegotiateDto negotiateDto;
    private int passenger;
    private int curPassenger;
    private String memo;

    @NotBlank
    private String request;

    private String departTime;

    private LocalDateTime createdDate;
    private LocalDateTime lastModifiedDate;

}
