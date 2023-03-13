package com.ski.backend.web.dto;

import com.ski.backend.domain.carpool.Negotiate;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeMap;
import org.modelmapper.config.Configuration;

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


