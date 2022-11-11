package com.project.ski.web.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigInteger;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class SubmitDto {
    private BigInteger id;
    private String username;
    private Integer submitState;
    private Integer equalUserState;
}
