package com.ski.backend.web.dto;

import com.ski.backend.domain.club.AgeGrp;
import com.ski.backend.domain.club.Gender;
import com.ski.backend.domain.user.Role;
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
    private String ageGrp;

}
