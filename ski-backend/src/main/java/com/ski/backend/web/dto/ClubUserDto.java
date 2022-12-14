package com.ski.backend.web.dto;

import com.ski.backend.domain.club.ClubUser;
import lombok.Builder;
import lombok.Data;

@Data
public class ClubUserDto {

    private Long userId;

    private String status;

    private String role;

    private String nickName;


    @Builder
    public ClubUserDto(long id, String status, String role, String nickName) {
        this.userId = id;
        this.status = status;
        this.role = role;
        this.nickName = nickName;
    }

    public ClubUser toEntity(ClubUser user){
        return ClubUser.builder()
                .id(user.getUser().getId())
                .status(user.getStatus())
                .role(user.getRole())
                .build();
    }

}
