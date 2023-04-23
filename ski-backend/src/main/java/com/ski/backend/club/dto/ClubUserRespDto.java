package com.ski.backend.club.dto;

import com.ski.backend.club.entity.ClubUser;
import com.ski.backend.club.entity.ClubRole;
import com.ski.backend.domain.common.Status;
import lombok.Data;




@Data
public class ClubUserRespDto {

    private long id;

    private String username;

    private String nickname;

    private ClubRole clubRole;

    private Status status;

    public ClubUserRespDto(ClubUser clubUser) {
        this.id = clubUser.getUser().getId();
        this.username = clubUser.getUser().getUsername();
        this.nickname = clubUser.getUser().getNickname();
        this.clubRole = clubUser.getClubRole();
        this.status = clubUser.getStatus();
    }

    public ClubUserRespDto(String username, Status status, ClubRole clubRole) {
        this.username = username;
        this.status = status;
        this.clubRole = clubRole;
    }
}
