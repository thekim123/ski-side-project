package com.ski.backend.domain.carpool;

import com.ski.backend.domain.BaseTimeEntity;
import lombok.*;

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
