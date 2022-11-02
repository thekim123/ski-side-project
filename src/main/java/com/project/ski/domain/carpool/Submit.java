package com.project.ski.domain.carpool;

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

    @JoinColumn(name = "fromUserId")
    @ManyToOne
    private User fromUser;

    @JoinColumn(name = "toCarpoolId")
    @ManyToOne
    private Carpool toCarpool;

    private int state;
    private LocalDateTime createDate;

    @PrePersist
    public void createDate() {
        this.createDate = LocalDateTime.now();
    }

}
