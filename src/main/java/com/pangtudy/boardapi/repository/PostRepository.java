package com.pangtudy.boardapi.repository;

import com.pangtudy.boardapi.dto.Post;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

public interface PostRepository extends ReactiveCrudRepository<Post, Integer> , CustomPostRepository{
}
