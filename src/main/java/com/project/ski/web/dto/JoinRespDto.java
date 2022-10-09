package com.project.ski.web.dto;

import com.project.ski.domain.user.User;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class JoinRespDto {
    private Long id;
    private String username;
    private String password;
    private String roles;

    public JoinRespDto toDto(User user){
        this.id = user.getId();
        this.username = user.getUsername();
        this.password = user.getPassword();
        this.roles = user.getRoles();
        return this;
    }
}
