package com.ski.backend.domain.club;

import com.ski.backend.domain.BaseTimeEntity;
import com.ski.backend.domain.user.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

import static javax.persistence.GenerationType.IDENTITY;


@Getter
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(
        uniqueConstraints = {
                @UniqueConstraint(
                        name = "enroll",
                        columnNames={"user_Id","club_Id"}
                )
        }
)
public class Enroll extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private long id;

    @JoinColumn(name = "user_Id")
    @ManyToOne
    private User fromUser;

    @JoinColumn(name = "club_Id")
    @ManyToOne
    private Club club;

    @Column(nullable = false)
    private String state;


    public void signup(User fromUser, Club club) {
        this.fromUser = fromUser;
        this.club = club;
        state = "0";
    }
}
