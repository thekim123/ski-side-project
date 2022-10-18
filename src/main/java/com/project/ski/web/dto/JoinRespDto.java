package com.project.ski.web.dto;

import com.project.ski.domain.user.Role;
import com.project.ski.domain.user.Users;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class JoinRespDto {
    private Long id;
    private String username;
    private String password;
    private Role roles;

    public JoinRespDto toDto(Users user){
        this.id = user.getId();
        this.username = user.getUsername();
        this.password = user.getPassword();
        this.roles = user.getRoles();
        return this;
    }
}
