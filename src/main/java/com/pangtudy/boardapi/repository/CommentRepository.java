package com.pangtudy.boardapi.repository;

import com.pangtudy.boardapi.dto.Comment;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

public interface CommentRepository extends ReactiveCrudRepository<Comment, Integer> {
}
