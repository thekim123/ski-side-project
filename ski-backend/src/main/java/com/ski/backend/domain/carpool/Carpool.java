package com.ski.backend.domain.carpool;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.ski.backend.domain.BaseTimeEntity;
import com.ski.backend.domain.user.User;
import com.ski.backend.web.dto.CarpoolRequestDto;
import com.ski.backend.web.dto.NegotiateDto;
import lombok.*;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeMap;
import org.modelmapper.config.Configuration;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;


// TODO : 카풀 - negotiate 엔티티 구조 개편 카풀로 통합, 아래 조건들도 통합할지 생각해봐야함.
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
    @JoinColumn(name = "userId")
    @ManyToOne
    private User user;

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

    /**
     * user, negotiate 를 매핑
     */
    public void withUserAndNegotiate(User user, Negotiate negotiate) {
        this.user = user;
        this.negotiate = negotiate;
    }

}
