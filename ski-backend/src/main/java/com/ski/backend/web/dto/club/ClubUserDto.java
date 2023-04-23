package com.ski.backend.web.dto.club;

import com.ski.backend.club.entity.ClubUser;
import com.ski.backend.club.entity.Role;
import lombok.Builder;
import lombok.Data;

@Data
public class ClubUserDto {

    private Long userId;

    private String status;

    private Role role;

    private String nickName;


    @Builder
    public ClubUserDto(long id, String status, Role role, String nickName) {
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
