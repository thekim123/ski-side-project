package com.ski.backend.user.dto;

import com.ski.backend.user.entity.Role;
import com.ski.backend.user.entity.User;
import lombok.*;

@NoArgsConstructor
@Getter
@Builder
@AllArgsConstructor
public class UserDto {
    private long id;
    private String password;
    private String username;
    private Role roles;
    private String nickname;
    private String gender;
    private Integer age;
    private String email;


    /**
     * user 엔티티 -> DTO 변환 메서드
     *
     * @param user user 엔티티
     * @return UserDto
     */
    public UserDto transformUserToDto(User user) {
        return UserDto.builder()
                .id(user.getId())
                .username(user.getUsername())
                .roles(user.getRoles())
                .gender(user.getGender() == null ? null : user.getGender().toString())
                .age(user.getAge())
                .nickname(user.getNickname())
                .email(user.getEmail())
                .build();
    }

}
