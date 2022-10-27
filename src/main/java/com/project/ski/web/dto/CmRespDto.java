package com.project.ski.web.dto;

import com.project.ski.domain.club.Club;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Page;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class CmRespDto<T> {
    private int code;
    private String message;
    private T data;

}
