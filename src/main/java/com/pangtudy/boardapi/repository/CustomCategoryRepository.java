package com.pangtudy.boardapi.repository;

import com.pangtudy.boardapi.entity.Category;
import reactor.core.publisher.Mono;

public interface CustomCategoryRepository {
    Mono<Category> findByIdWithPosts(Integer id);
}
