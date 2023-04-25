package com.skiproject.sseserver.notification.dto;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class NotificationDto {

    private String id;
    private String notification;
    private String receiver;
    private String url;
    private boolean read;
    private LocalDateTime createdAt;

}
