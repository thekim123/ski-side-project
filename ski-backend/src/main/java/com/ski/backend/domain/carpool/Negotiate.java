package com.ski.backend.domain.carpool;

import com.ski.backend.domain.BaseTimeEntity;
import com.ski.backend.web.dto.NegotiateDto;
import lombok.*;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeMap;
import org.modelmapper.config.Configuration;

import javax.persistence.*;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@EqualsAndHashCode(callSuper = true)
@ToString
@Embeddable
public class Negotiate extends BaseTimeEntity {

    private boolean nDeparture;
    private boolean nDestination;
    private boolean nDepartTime;
    private boolean nBoardingPlace;

}
