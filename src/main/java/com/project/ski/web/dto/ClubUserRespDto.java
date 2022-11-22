package com.project.ski.web.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.project.ski.domain.Tayo.Tayo;
import com.project.ski.domain.board.Board;
import com.project.ski.domain.carpool.Carpool;
import com.project.ski.domain.club.Club;
import com.project.ski.domain.club.ClubUser;
import com.project.ski.domain.user.Role;
import com.project.ski.domain.user.User;
import lombok.Data;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

import static javax.persistence.FetchType.LAZY;


@Data
public class ClubUserRespDto {

    private long id;

    private String username;

    private String nickname;


    public ClubUserRespDto(ClubUser clubUser) {
        this.id = clubUser.getUser().getId();
        this.username = clubUser.getUser().getUsername();
        this.nickname = clubUser.getUser().getNickname();
    }
}
