package com.ski.backend.domain.carpool;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.ski.backend.domain.user.User;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Getter
@Table(
        uniqueConstraints = {
                @UniqueConstraint(
                        name = "submit_uk",
                        columnNames = {"fromUserId", "toCarpoolId"}
                )
        }
)
public class Submit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @JsonIgnoreProperties({"password", "boards", "clubUsers", "carpools", "tayos"})
    @JoinColumn(name = "fromUserId")
    @ManyToOne
    private User fromUser;

    @JsonIgnoreProperties({"user"})
    @JoinColumn(name = "toCarpoolId")
    @ManyToOne
    private Carpool toCarpool;

    @Enumerated(EnumType.STRING)
    private SubmitState state;
    private LocalDateTime createDate;

    @PrePersist
    public void createDate() {
        this.createDate = LocalDateTime.now();
    }

    public void setState(String state) {
        this.state = SubmitState.valueOf(state);
    }
}
