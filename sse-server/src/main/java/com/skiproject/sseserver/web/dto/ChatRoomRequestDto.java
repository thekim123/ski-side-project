package com.skiproject.sseserver.web.dto;

import lombok.Builder;
import lombok.Data;

@Data
public class ChatRoomRequestDto {
    private String roomName;
    private String sender;
}
