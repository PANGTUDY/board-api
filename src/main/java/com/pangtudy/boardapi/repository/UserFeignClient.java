package com.pangtudy.boardapi.repository;

import com.pangtudy.boardapi.dto.UserResponse;
import com.pangtudy.boardapi.dto.UsersResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import reactivefeign.spring.config.ReactiveFeignClient;
import reactor.core.publisher.Mono;

@ReactiveFeignClient(name = "user-api", url="http://ec2-54-242-72-201.compute-1.amazonaws.com:8080")
@Component
public interface UserFeignClient
{
    @GetMapping(value = "/users", produces = "application/json")
    Mono<UsersResponse> getUsers();

    @GetMapping(value = "/users/{id}", produces = "application/json")
    Mono<UserResponse> getUser(@PathVariable("id") Integer userId);
}