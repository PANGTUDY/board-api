package com.pangtudy.boardapi.repository;

import com.pangtudy.boardapi.entity.Comment;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;

public interface CommentRepository extends ReactiveCrudRepository<Comment, Integer>, CustomCommentRepository{
    Flux<Comment> findCommentByPostId(Integer postId);
}
