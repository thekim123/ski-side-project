package com.ski.backend.web.dto;

import com.ski.backend.domain.club.AgeGrp;
import com.ski.backend.domain.club.Gender;
import com.ski.backend.domain.user.Role;
import com.ski.backend.domain.user.User;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class UserRespDto {
    private String username;
    private Role roles;
    private String nickname;
    private String email;
    private Gender gender;
    private AgeGrp ageGrp;


    public UserRespDto toDto(User user) {
        this.username = user.getUsername();
        this.roles = user.getRoles();
        this.email = user.getEmail();
        this.gender = user.getGender();
        this.ageGrp = user.getAgeGrp();
        this.nickname = user.getNickname();
        return this;
    }
}
