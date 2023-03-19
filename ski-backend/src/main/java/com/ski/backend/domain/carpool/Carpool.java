package com.ski.backend.domain.carpool;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.ski.backend.domain.user.User;
import com.ski.backend.web.dto.CarpoolRequestDto;
import com.ski.backend.web.dto.NegotiateDto;
import lombok.*;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeMap;
import org.modelmapper.config.Configuration;
import org.springframework.boot.context.properties.bind.DefaultValue;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;


/**
 * Carpool entity
 * curPassenger의 Default Value를
 * 생성자에 만들어야 될지, 그냥 필드에 0을 때려박을지 고민중이에요.
 * 일단은 withUserAndNegotiate 메서드 안에 넣겠습니다.
 */

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
public class Carpool {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JsonIgnoreProperties({"boards", "clubUsers", "carpools", "tayoUsers", "password", "bookmarks"})
    @JoinColumn(name = "userId")
    @ManyToOne
    private User user;

    @OneToOne(cascade = CascadeType.ALL)
    private Negotiate negotiate;

    private int curPassenger;
    private String departure;
    private String destination;
    private int passenger;
    private String memo;
    private LocalDateTime departTime;
    private boolean isSmoker;
    private String boarding;

    @Enumerated(EnumType.STRING)
    private RequestType request;

    @JsonIgnoreProperties({"fromUser", "toCarpool"})
    @OneToMany(mappedBy = "toCarpool", cascade = CascadeType.ALL)
    private List<Submit> submits;

    private LocalDateTime createDate;

    @PrePersist
    public void createDate() {
        this.createDate = LocalDateTime.now();
    }


    public void setCurPassengerWithDefaultValue() {
        this.curPassenger = 0;
    }

    public void increaseCurPassenger() {
        this.curPassenger++;
    }

    /**
     * user, negotiate 를 매핑
     */
    public void withUserAndNegotiate(User user, Negotiate negotiate) {
        this.user = user;
        this.negotiate = negotiate;
    }

    /**
     * dto -> entity 매핑
     */
    public void mapEntityWhenUpdate(CarpoolRequestDto.Save dto) {
        ModelMapper mapper = new ModelMapper();
        mapper.getConfiguration()
                .setFieldAccessLevel(Configuration.AccessLevel.PRIVATE)
                .setFieldMatchingEnabled(true);
        TypeMap<CarpoolRequestDto.Save, Carpool> carpoolTypeMap
                = mapper.createTypeMap(CarpoolRequestDto.Save.class, Carpool.class);
        typeMapWithCarpools(carpoolTypeMap);
        mapper.map(dto, this);
    }

    public void typeMapWithCarpools(TypeMap<CarpoolRequestDto.Save, Carpool> typeMap) {
        typeMap.setProvider(request -> {
            CarpoolRequestDto.Save source = (CarpoolRequestDto.Save) request.getSource();
            return Carpool.builder()
                    .id(source.getId())
                    .departure(source.getDeparture())
                    .destination(source.getDestination())
                    .boarding(source.getBoarding())
                    .isSmoker(source.isSmoker())
                    .passenger(source.getPassenger())
                    .memo(source.getMemo())
                    .request(RequestType.valueOf(source.getRequest()))
                    .negotiate(typeMapWithNegotiate().map(source.getNegotiateDto()))
                    .departTime(LocalDateTime.parse(source.getDepartTime(), DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")))
                    .build();
        });
    }

    public TypeMap<NegotiateDto, Negotiate> typeMapWithNegotiate() {
        ModelMapper mapper = new ModelMapper();
        TypeMap<NegotiateDto, Negotiate> typeMap = mapper.createTypeMap(NegotiateDto.class, Negotiate.class);
        typeMap.setProvider(request -> {
            NegotiateDto source = (NegotiateDto) request.getSource();
            return Negotiate.builder()
                    .boardingPlace(source.isBoardingPlace())
                    .destination(source.isDestination())
                    .departTime(source.isDepartTime())
                    .departure(source.isDeparture())
                    .build();
        });
        return typeMap;
    }
}
