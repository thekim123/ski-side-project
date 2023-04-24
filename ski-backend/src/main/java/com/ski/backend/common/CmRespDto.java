package com.ski.backend.common;

import lombok.AllArgsConstructor;
import lombok.Builder;
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
