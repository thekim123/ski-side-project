package com.ski.backend.club.dto;

import com.ski.backend.club.entity.ClubUser;
import com.ski.backend.club.entity.ClubRole;
import lombok.Builder;
import lombok.Data;

@Data
public class ClubUserDto {

    private Long userId;

    private String status;

    private ClubRole clubRole;

    private String nickName;


    @Builder
    public ClubUserDto(long id, String status, ClubRole clubRole, String nickName) {
        this.userId = id;
        this.status = status;
        this.clubRole = clubRole;
        this.nickName = nickName;
    }

    public ClubUser toEntity(ClubUser user){
        return ClubUser.builder()
                .id(user.getUser().getId())
                .status(user.getStatus())
                .clubRole(user.getClubRole())
                .build();
    }

}
