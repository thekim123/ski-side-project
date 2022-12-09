package com.ski.backend.web.dto;

import com.ski.backend.domain.club.ClubUser;
import lombok.Data;




@Data
public class ClubUserRespDto {

    private long id;

    private String username;

    private String nickname;

    private String role;

    public ClubUserRespDto(ClubUser clubUser) {
        this.id = clubUser.getUser().getId();
        this.username = clubUser.getUser().getUsername();
        this.nickname = clubUser.getUser().getNickname();
        this.role = clubUser.getRole();
    }
}
