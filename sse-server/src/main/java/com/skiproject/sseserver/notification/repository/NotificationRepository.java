package com.skiproject.sseserver.notification.repository;

import com.skiproject.sseserver.notification.entity.Notification;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.data.mongodb.repository.Tailable;
import reactor.core.publisher.Flux;

public interface NotificationRepository extends ReactiveMongoRepository<Notification, String> {

    /**
     * TODO: 이것도 JPA 네이밍 메서드가 되는지 확인
     *
     * @param receiver 알림 수신자
     * @param read     알림을 읽었는지 여부
     * @return 알림 flux
     */
    @Tailable
    Flux<Notification> findByReceiverAndRead(String receiver, boolean read);

}
