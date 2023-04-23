package com.ski.backend.carpool.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class NegotiateDto {
    private boolean nDeparture;
    private boolean nDestination;
    private boolean nDepartTime;
    private boolean nBoardingPlace;
}


