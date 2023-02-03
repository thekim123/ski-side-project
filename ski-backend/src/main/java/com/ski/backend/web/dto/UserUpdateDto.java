package com.ski.backend.web.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class UserUpdateDto {

    private String username;
    private String password;
    private String roles;
    private String nickname;
    private String email;
    private String gender;
    private Integer age;
    private Boolean agreement;

}
