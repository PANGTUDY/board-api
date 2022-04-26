package com.pangtudy.boardapi.repository;

import com.pangtudy.boardapi.entity.Likes;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface CustomLikesRepository {
    Flux<Likes> findByUserId(Integer userId);
    Flux<Likes> findByPostId(Integer userId);
    Mono<Long> findPostLikeCount(Integer userId);
    Mono<Integer> deleteByPostIdAndUserId(Integer postId, Integer userId);
    Mono<Likes> insertUserId(Integer postId, Integer userId);
}
