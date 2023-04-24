package com.ski.backend.carpool.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.ski.backend.common.BaseTimeEntity;
import com.ski.backend.user.entity.User;
import lombok.*;

import javax.persistence.*;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Getter
@EqualsAndHashCode(callSuper = true)
@ToString
@Table(
        uniqueConstraints = {
                @UniqueConstraint(
                        name = "submit_uk",
                        columnNames = {"fromUserId", "toCarpoolId"}
                )
        }
)
public class Submit extends BaseTimeEntity {

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

    public void setState(String state) {
        this.state = SubmitState.valueOf(state);
    }
}
