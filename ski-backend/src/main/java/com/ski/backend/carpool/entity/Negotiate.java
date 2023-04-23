package com.ski.backend.carpool.entity;

import com.ski.backend.domain.BaseTimeEntity;
import lombok.*;

import javax.persistence.*;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@ToString
@Embeddable
public class Negotiate {

    private boolean nDeparture;
    private boolean nDestination;
    private boolean nDepartTime;
    private boolean nBoardingPlace;

}