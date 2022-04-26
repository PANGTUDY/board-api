package com.pangtudy.boardapi.repository;

import com.pangtudy.boardapi.entity.Category;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

public interface CategoryRepository extends ReactiveCrudRepository<Category, Integer>, CustomCategoryRepository {
}
