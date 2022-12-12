package com.ski.backend.web.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class CmRespDto<T> {
    private int code;
    private String message;
    private T data;

}
