package com.skiproject.sseserver.notification.contorller;

import com.skiproject.sseserver.notification.dto.NotificationDto;
import com.skiproject.sseserver.notification.entity.Notification;
import com.skiproject.sseserver.notification.repository.NotificationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.reactive.function.server.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import java.time.LocalDateTime;

/**
 * @author thekim123
 * @apiNote 알림을 실시간으로 발송하고 읽은 알림을 체크하는 api 컨트롤러
 * 누군가 댓글을 쓴다 -> 알림을 서버에 저장한다 ->
 * 서버는 다시 수신자에게 알림을 발송한다 -> 수신자가 읽으면 읽음 처리한다.
 * -> 언제 삭제 할지는 기획자 분과 얘기
 * <p/>
 * <b> Service 레이어를 두지 않은 이유</b>
 * <br/>
 * Spring WebFlux 에서는 일반적으로 컨트롤러(Controller)와
 * 리액티브한 데이터 처리를 위한 레포지토리(Repository) 클래스가 사용됩니다.
 * 이 때, 비즈니스 로직을 처리하는 서비스(Service) 레이어가
 * 별도로 존재하지 않아도 됩니다.
 * @since 2023.04.25
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/notification")
public class NotificationController {

    private final NotificationRepository notificationRepository;

    /**
     * @return 알림 Mono
     * @apiNote 비즈니스 복잡도가 너무 낮고,
     * Spring Webflux 특성상 별도로 서비스 레이어를 두지 않고
     * 컨트롤러 -> 레포지토리 레이어에서 바로 처리하는 방식으로 함
     */
    @CrossOrigin
    @PostMapping
    public Mono<?> takeNotification(@RequestBody NotificationDto dto) {
        Notification notification = Notification.builder()
                .url(dto.getUrl())
                .receiver(dto.getReceiver())
                .createdAt(LocalDateTime.now())
                .notification(dto.getNotification())
                .read(false)
                .build();
        return notificationRepository.save(notification);
    }

    /**
     * TODO: 몽고 디비 가서 버퍼사이즈 조정할 것
     *
     * @param receiver 수신자의 username 을 보내주세요.
     * @return 알림의 Flux
     * @apiNote 알림 Flux 를 개통하는 api
     * @since 2023.04.25
     */
    @CrossOrigin
    @GetMapping(value = "{receiver}", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<?> getNotificationFlux(@PathVariable String receiver) {
        return notificationRepository.findByReceiverAndRead(receiver, false)
                .subscribeOn(Schedulers.boundedElastic());
    }

    /**
     * 알림을 읽었을 때 호출하는 api
     * TODO: 몽고디비 collection 버퍼 사이즈 조정 후 테스트 해야함.
     *
     * @param notificationId 알림 id
     * @return 메서드의 반환 타입은 void 이지만,
     * 비동기적으로 처리되는 Reactive 방식으로
     * Mono 타입의 결과가 반환된다.
     * @apiNote findById 로 알림을 검색한다.
     * flatMap 을 이용하여 읽음(read) 상태를 true 로 바꾼 후
     * save 를 통해서 해당 내용을 db에 update 한다.
     * @since 2023.04.25
     */
    @CrossOrigin
    @PutMapping("/{notificationId}")
    public void readNotification(@PathVariable String notificationId) {
        notificationRepository.findById(notificationId).flatMap(target -> {
                    target.read();
                    return notificationRepository.save(target);
                }).thenReturn(ServerResponse.ok()
                        .contentType(MediaType.TEXT_PLAIN)
                        .body("읽음 처리가 완료되었습니다.", String.class))
                .subscribe();
    }
}

