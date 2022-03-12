package com.pangtudy.boardapi.repository;

import com.pangtudy.boardapi.dto.Post;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface CustomPostRepository {
    Mono<Post> findByIdWithComments(Integer id);
    Flux<Post> findPostByTitleContains(String title);
}
