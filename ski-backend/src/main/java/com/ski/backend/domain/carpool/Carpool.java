package com.ski.backend.domain.carpool;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.ski.backend.domain.user.User;
import com.ski.backend.web.dto.CarpoolRequestDto;
import lombok.*;
import org.springframework.boot.context.properties.bind.DefaultValue;

import javax.persistence.*;
import java.time.LocalDateTime;
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

    private String request;

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

}
