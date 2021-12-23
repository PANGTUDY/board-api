package com.pangtudy.boardapi.repository;

import com.pangtudy.boardapi.dto.Comment;
import com.pangtudy.boardapi.dto.Post;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Mono;

public interface CommentRepository extends ReactiveCrudRepository<Comment, Integer> {
    Mono<Comment> findByPostId(Integer postId);
}
