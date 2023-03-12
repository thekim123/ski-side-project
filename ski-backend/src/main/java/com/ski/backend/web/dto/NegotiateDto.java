package com.ski.backend.web.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class NegotiateDto {
    private Long id;
    private boolean departure;
    private boolean destination;
    private boolean departTime;
    private boolean boardingPlace;
}


