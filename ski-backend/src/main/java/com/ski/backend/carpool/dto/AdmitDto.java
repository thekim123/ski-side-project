package com.ski.backend.carpool.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AdmitDto {

    private long admitUserId;
    private long toCarpoolId;

}
