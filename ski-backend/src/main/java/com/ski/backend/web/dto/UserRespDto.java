package com.ski.backend.web.dto;

import com.ski.backend.domain.user.Role;
import com.ski.backend.domain.user.User;
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
