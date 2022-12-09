package com.ski.backend.web.dto;

import com.ski.backend.domain.user.Role;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class UserUpdateDto {

    private String username;
    private String password;
    private Role roles;

}
