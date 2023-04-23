package com.ski.backend.club.dto;

import com.ski.backend.club.entity.ClubRole;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ClubUserUpdateDto {

    private String status;
    private ClubRole clubRole;

    @Builder
    public ClubUserUpdateDto(String status, ClubRole clubRole) {
        this.status = status;
        this.clubRole = clubRole;
    }
}
