package com.ski.backend.web.dto;

import com.ski.backend.domain.club.AgeGrp;
import com.ski.backend.domain.club.Gender;
import com.ski.backend.domain.user.Role;
import com.ski.backend.domain.user.User;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class UserDto {
    private long id;
    private String password;
    private String username;
    private Role roles;
    private String nickname;
    private String email;
    private String gender;
    private String ageGrp;


    public UserDto toDto(User user) {
        this.id = user.getId();
        this.username = user.getUsername();
        this.roles = user.getRoles();
        this.email = user.getEmail();
        this.gender = user.getGender().toString();
        this.ageGrp = user.getAgeGrp().toString();
        this.nickname = user.getNickname();
        return this;
    }
}
