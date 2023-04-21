package com.ski.backend.domain.board;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.ski.backend.domain.BaseTimeEntity;
import com.ski.backend.domain.resort.Resort;
import com.ski.backend.domain.user.User;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Builder
@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "Bookmark", uniqueConstraints = {@UniqueConstraint(name = "subscribe", columnNames = {"fromUserId", "toResortId"})})
@EqualsAndHashCode(callSuper = true)
@ToString
public class Bookmark extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JsonIgnoreProperties({"boards", "password", "clubUsers", "carpools", "tayos", "dislikes", "likes", "chatRooms", "whispers"})
    @ManyToOne
    @JoinColumn(name = "fromUserId")
    private User fromUser;

    @JsonIgnoreProperties
    @ManyToOne
    @JoinColumn(name = "toResortId")
    private Resort toResort;

}
