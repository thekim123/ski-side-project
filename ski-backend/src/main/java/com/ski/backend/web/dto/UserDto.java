package com.ski.backend.web.dto;

import com.ski.backend.domain.user.Role;
import com.ski.backend.domain.user.User;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class UserDto {
    private long id;
    private String password;
    private String username;
    private Role roles;
    private String nickname;
    private String gender;
    private Integer age;


    public UserDto toDto(User user) {
        this.id = user.getId();
        this.username = user.getUsername();
        this.roles = user.getRoles();
        this.gender = user.getGender() == null ? null : user.getGender().toString();
        this.age = user.getAge();
        this.nickname = user.getNickname();
        return this;
    }
}
