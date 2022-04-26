package com.pangtudy.boardapi.repository;

import com.pangtudy.boardapi.entity.Tag;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

public interface TagRepository extends ReactiveCrudRepository<Tag, Integer> {
}
