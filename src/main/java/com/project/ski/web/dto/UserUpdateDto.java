package com.project.ski.web.dto;

import com.project.ski.domain.user.Role;
import com.project.ski.domain.user.User;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class UserUpdateDto {

    private String username;
    private String password;
    private Role roles;

    public UserUpdateDto toDto(User user) {
        this.username = user.getUsername();
        this.password = user.getPassword();
        this.roles = user.getRoles();
        return this;
    }
}
