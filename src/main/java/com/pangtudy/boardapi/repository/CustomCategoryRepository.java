package com.pangtudy.boardapi.repository;

import com.pangtudy.boardapi.dto.Category;
import reactor.core.publisher.Mono;

public interface CustomCategoryRepository {
    Mono<Category> findByIdWithPosts(Integer id);
}
