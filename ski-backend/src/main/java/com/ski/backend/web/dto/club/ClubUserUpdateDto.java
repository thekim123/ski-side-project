package com.ski.backend.web.dto.club;

import com.ski.backend.club.entity.Role;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ClubUserUpdateDto {

    private String status;
    private Role role;

    @Builder
    public ClubUserUpdateDto(String status, Role role) {
        this.status = status;
        this.role = role;
    }
}
