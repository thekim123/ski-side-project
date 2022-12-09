package com.skiproject.sseserver.web.dto;

import lombok.Builder;
import lombok.Data;


@Builder
@Data
public class ChatResponseDto {

    private String msg;
    private String sender;
    private String roomName;

}
