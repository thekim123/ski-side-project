package com.ski.backend.domain.carpool;

import com.fasterxml.jackson.annotation.JsonIncludeProperties;
import com.ski.backend.web.dto.NegotiateDto;
import lombok.*;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeMap;
import org.modelmapper.config.Configuration;

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


    public void mapEntityWhenUpdate(NegotiateDto dto) {
        ModelMapper mapper = new ModelMapper();
        mapper.getConfiguration()
                .setFieldAccessLevel(Configuration.AccessLevel.PRIVATE)
                .setFieldMatchingEnabled(true);
        TypeMap<NegotiateDto, Negotiate> negotiateTypeMap
                = mapper.createTypeMap(NegotiateDto.class, Negotiate.class);
        typeMapWithNegotiate(negotiateTypeMap);
        mapper.map(dto, this);
    }

    private void typeMapWithNegotiate(TypeMap<NegotiateDto, Negotiate> typeMap) {
        typeMap.setProvider(request -> {
            NegotiateDto source = (NegotiateDto) request.getSource();
            return Negotiate.builder()
                    .boardingPlace(source.isBoardingPlace())
                    .destination(source.isDestination())
                    .departTime(source.isDepartTime())
                    .departure(source.isDeparture())
                    .build();
        });
    }

}
