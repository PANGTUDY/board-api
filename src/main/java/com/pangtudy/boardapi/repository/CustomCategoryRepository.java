package com.pangtudy.boardapi.repository;

import com.pangtudy.boardapi.dto.Category;
import reactor.core.publisher.Flux;

public interface CustomCategoryRepository {
    Flux<Category> findByIdWithPosts(Integer id);
}
