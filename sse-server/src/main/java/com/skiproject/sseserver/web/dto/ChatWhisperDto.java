package com.skiproject.sseserver.web.dto;

import lombok.Data;

@Data
public class ChatWhisperDto {
    private String receiver;
    private String sender;
}
