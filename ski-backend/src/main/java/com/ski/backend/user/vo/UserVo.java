package com.ski.backend.user.vo;


import com.ski.backend.club.entity.Gender;
import com.ski.backend.user.entity.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@Builder
@NoArgsConstructor
@Getter
public class UserVo {

    private String username;
    private String password;
    private Role role;
    private String nickname;
    private String email;
    private Gender gender;
    private Integer age;
    private Boolean agreement;

}
