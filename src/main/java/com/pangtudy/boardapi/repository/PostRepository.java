package com.pangtudy.boardapi.repository;

import com.pangtudy.boardapi.dto.Post;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface PostRepository extends ReactiveCrudRepository<Post, Integer> , CustomPostRepository{
    Flux<Post> findPostByCategoryId(Integer categoryId);
    Mono<Post> findPostByWriter(String writer);
    Mono<Post> findPostByTitle(String title);
}
