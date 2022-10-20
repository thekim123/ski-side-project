package com.project.ski.web.dto;

import com.project.ski.domain.user.Role;
import com.project.ski.domain.user.User;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class UserRespDto {
    private String username;
    private Role roles;

    public UserRespDto toDto(User user){
        this.username = user.getUsername();
        this.roles = user.getRoles();
        return this;
    }
}
