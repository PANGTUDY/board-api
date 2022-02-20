package com.pangtudy.boardapi.repository;

import com.pangtudy.boardapi.dto.Post;
import reactor.core.publisher.Flux;

public interface CustomPostRepository {
    Flux<Post> findByIdWithComments(Integer id);
}
