package com.ski.backend.board.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.ski.backend.common.BaseTimeEntity;
import com.ski.backend.resort.entity.Resort;
import com.ski.backend.user.entity.User;
import lombok.*;

import javax.persistence.*;

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
