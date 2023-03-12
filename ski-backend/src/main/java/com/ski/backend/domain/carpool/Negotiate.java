package com.ski.backend.domain.carpool;

import com.fasterxml.jackson.annotation.JsonIncludeProperties;
import lombok.*;

import javax.persistence.*;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
public class Negotiate {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JsonIncludeProperties({"id"})
    @OneToOne
    private Carpool carpool;

    private boolean departure;
    private boolean destination;
    private boolean departTime;
    private boolean boardingPlace;

}
