package com.pangtudy.boardapi.repository;

import com.pangtudy.boardapi.dto.Post;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;

public interface PostRepository extends ReactiveCrudRepository<Post, Integer> , CustomPostRepository{
    Flux<Post> findPostByCategoryId(Integer categoryId);
    Flux<Post> findPostByWriter(String writer);
}
