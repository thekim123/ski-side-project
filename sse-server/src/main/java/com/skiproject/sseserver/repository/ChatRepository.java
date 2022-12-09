package com.skiproject.sseserver.repository;

import com.skiproject.sseserver.domain.Chat;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.data.mongodb.repository.Tailable;
import reactor.core.publisher.Flux;

public interface ChatRepository extends ReactiveMongoRepository<Chat, String> {

    @Tailable
    @Query("{roomName:  ?0}")
    Flux<Chat> mFindByRoomName(String roomName);

    @Tailable
    @Query("{ sender : ?0, receiver : ?1}")
    Flux<Chat> mFindBySender(String sender, String receiver);

}
