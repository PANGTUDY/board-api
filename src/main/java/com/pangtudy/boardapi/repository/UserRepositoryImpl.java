package com.pangtudy.boardapi.repository;

import com.pangtudy.boardapi.dto.User;
import com.pangtudy.boardapi.dto.UserResponse;
import com.pangtudy.boardapi.dto.UsersResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
@RequiredArgsConstructor
public class UserRepositoryImpl implements UserRepository{
    private final UserFeignClient userFeignClient;

    @Override
    public Flux<User> getAllUser() {
        Mono<UsersResponse> userResponseMono = this.userFeignClient.getUsers();
        return userResponseMono.map(UsersResponse::getData)
                .flatMapIterable(list->list)
                .doOnNext(System.out::println)
                .doFinally(s -> System.out.println("------------- GET /users completed ------------------"));
    }

    @Override
    public Mono<User> getUser(Integer userId) {
        Mono<UserResponse> userResponseMono = this.userFeignClient.getUser(userId);
        return userResponseMono.map(UserResponse::getData)
                .doOnNext(System.out::println)
                .doFinally(s -> System.out.println("------------- GET /users/{id} completed ------------------"));
    }
}
