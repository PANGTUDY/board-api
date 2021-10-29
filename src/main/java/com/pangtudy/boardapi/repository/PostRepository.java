package com.pangtudy.boardapi.repository;

import com.pangtudy.boardapi.dto.Post;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Mono;

public interface PostRepository extends ReactiveCrudRepository<Post, Integer> {
    Mono<Post> findPostByCategoryId(Integer categoryId);
    Mono<Post> findPostByWriter(Integer postId);
    Mono<Post> findPostByTitle(String Title);
}
