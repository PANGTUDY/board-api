package com.pangtudy.boardapi.repository;

import com.pangtudy.boardapi.dto.Category;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

public interface CategoryRepository extends ReactiveCrudRepository<Category, Integer> {

}
