package com.skiproject.sseserver.notification.entity;

import com.skiproject.sseserver.notification.dto.NotificationDto;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

/**
 * @author thekim123
 * @apiNote 알림 entity (webflux 에서도 entity인가?)
 * @since 2023.04.25
 */
@Getter
@Builder
@Document(collection = "notification")
public class Notification {

    private String id;
    private String notification;
    private String receiver;
    private String url;
    private boolean read;
    private LocalDateTime createdAt;

    public void read() {
        this.read = true;
    }
}
