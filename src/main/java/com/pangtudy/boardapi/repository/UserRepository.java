package com.pangtudy.boardapi.repository;

import com.pangtudy.boardapi.dto.User;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface UserRepository {
    Flux<User> getAllUser();
    Mono<User> getUser(Integer userId);
}
