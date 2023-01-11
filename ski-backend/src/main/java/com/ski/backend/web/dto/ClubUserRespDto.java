package com.ski.backend.web.dto;

import com.ski.backend.domain.club.ClubUser;
import com.ski.backend.domain.club.Role;
import com.ski.backend.domain.common.Status;
import lombok.Data;




@Data
public class ClubUserRespDto {

    private long id;

    private String username;

    private String nickname;

    private Role role;

    private Status status;

    public ClubUserRespDto(ClubUser clubUser) {
        this.id = clubUser.getUser().getId();
        this.username = clubUser.getUser().getUsername();
        this.nickname = clubUser.getUser().getNickname();
        this.role = clubUser.getRole();
        this.status = clubUser.getStatus();
    }

    public ClubUserRespDto(String username, Status status, Role role) {
        this.username = username;
        this.status = status;
        this.role = role;
    }
}
