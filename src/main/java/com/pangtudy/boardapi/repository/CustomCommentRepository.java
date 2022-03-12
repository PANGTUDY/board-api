package com.pangtudy.boardapi.repository;

import com.pangtudy.boardapi.dto.Comment;
import reactor.core.publisher.Mono;

public interface CustomCommentRepository {
    Mono<Comment> findCommentByPostIdAndCommentId(Integer postId, Integer commentId);
}
