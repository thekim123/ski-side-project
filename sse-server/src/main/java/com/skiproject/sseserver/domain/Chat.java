package com.skiproject.sseserver.domain;

import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Data
@Document(collection = "chat")
public class Chat {
    private String id;
    private String msg;
    private String sender;
    private String receiver;
    private String roomName;

    private LocalDateTime createdAt;
}
