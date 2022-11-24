package com.project.ski.domain.carpool;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.project.ski.domain.user.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Data
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

    @JsonIgnoreProperties({"user", "boards"})
    @JoinColumn(name = "fromUserId")
    @ManyToOne
    private User fromUser;

    @JsonIgnoreProperties({"user"})
    @JoinColumn(name = "toCarpoolId")
    @ManyToOne
    private Carpool toCarpool;

    private String state;
    private LocalDateTime createDate;

    @PrePersist
    public void createDate() {
        this.createDate = LocalDateTime.now();
    }

}
