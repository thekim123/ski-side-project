package com.ski.backend.web.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
public class LoginRequestDto {
    private String username;
    private String password;
}
