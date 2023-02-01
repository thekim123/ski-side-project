package com.ski.backend.domain.user;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonIncludeProperties;
import com.ski.backend.domain.club.Club;
import com.ski.backend.domain.club.ClubUser;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class ChatRoom {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JsonIncludeProperties({"username", "id"})
    @ManyToOne
    private User user;

    @Column
    private String roomName;

    @JsonIgnoreProperties({"clubUsers"})
    @OneToOne
    private Club club;
}
