package com.ski.backend.web.dto;

import lombok.Data;

@Data
public class EnrollRespDto {

    private long userId;

    private long clubId;

    public EnrollRespDto(long userId, long clubId) {
        this.userId = userId;
        this.clubId = clubId;
    }

}
