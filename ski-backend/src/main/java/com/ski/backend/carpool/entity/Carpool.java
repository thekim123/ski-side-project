package com.ski.backend.carpool.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.ski.backend.common.BaseTimeEntity;
import com.ski.backend.user.entity.User;
import com.ski.backend.carpool.dto.CarpoolRequestDto;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@EqualsAndHashCode(callSuper = true)
@ToString
public class Carpool extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JsonIgnoreProperties({"boards", "clubUsers", "carpools", "tayoUsers", "password", "bookmarks"})
    @JoinColumn(name = "writerId")
    @ManyToOne
    private User writer;

    @Embedded
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
    @Builder.Default
    private List<Submit> submits = new ArrayList<>();

    public void setCurPassengerWithDefaultValue() {
        this.curPassenger = 0;
    }

    public void increaseCurPassenger() {
        this.curPassenger++;
    }


    public void update(CarpoolRequestDto dto) {
        this.passenger = dto.getPassenger();
        this.departure = dto.getDeparture();
        this.destination = dto.getDestination();
        this.boarding = dto.getBoarding();
        this.isSmoker = dto.isSmoker();
        this.memo = dto.getMemo();
        this.request = RequestType.valueOf(dto.getRequest());
        this.departTime = LocalDateTime.parse(dto.getDepartTime());
    }

    /**
     * user, negotiate 를 매핑
     */
    public void withWriter(User user) {
        this.writer = user;
    }

}
