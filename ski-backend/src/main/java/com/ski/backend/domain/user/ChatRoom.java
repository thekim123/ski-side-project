package com.ski.backend.domain.user;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonIncludeProperties;
import com.ski.backend.domain.BaseTimeEntity;
import com.ski.backend.domain.club.Club;
import com.ski.backend.domain.club.ClubUser;
import lombok.*;

import javax.persistence.*;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
@EqualsAndHashCode(callSuper = true)
@ToString
public class ChatRoom extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JsonIncludeProperties({"username", "id"})
    @ManyToOne
    @JoinColumn(name = "userId")
    private User user;

    @Column
    private String roomName;

    @JsonIgnoreProperties({"clubUsers", "resort"})
    @OneToOne
    private Club club;
}
