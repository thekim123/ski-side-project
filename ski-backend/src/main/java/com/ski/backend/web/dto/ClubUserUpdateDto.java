package com.ski.backend.web.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ClubUserUpdateDto {

    private String status;
    private String role;

    @Builder
    public ClubUserUpdateDto(String status, String role) {
        this.status = status;
        this.role = role;
    }
}
