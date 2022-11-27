package com.project.ski.web.dto;

import com.project.ski.domain.user.Role;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class UserUpdateDto {

    private String username;
    private String password;
    private Role roles;

}
