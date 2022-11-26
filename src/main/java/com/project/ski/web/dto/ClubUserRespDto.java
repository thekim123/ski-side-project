package com.project.ski.web.dto;

import com.project.ski.domain.club.ClubUser;
import lombok.Data;




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
