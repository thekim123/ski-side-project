package com.ski.backend.chat.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Chat {
    private String sender;
    private String receiver;
    private String msg;
    private String roomName;
}
