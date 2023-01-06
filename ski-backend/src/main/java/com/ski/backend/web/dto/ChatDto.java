package com.ski.backend.web.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ChatDto {
    private String sender;
    private String receiver;
    private String msg;
    private String roomName;
    private LocalDateTime createdAt;
}
