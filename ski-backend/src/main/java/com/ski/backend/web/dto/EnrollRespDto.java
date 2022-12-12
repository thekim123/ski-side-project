package com.ski.backend.web.dto;

import com.ski.backend.domain.club.Enroll;
import lombok.Data;

@Data
public class EnrollRespDto {

    private long userId;

    private long clubId;

    public EnrollRespDto(Enroll enroll) {
        this.clubId = enroll.getClub().getId();
        this.userId = enroll.getFromUser().getId();
    }
}
