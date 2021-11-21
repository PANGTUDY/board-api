package com.pangtudy.boardapi.repository;

import com.pangtudy.boardapi.dto.Content;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

public interface ContentRepository extends ReactiveCrudRepository<Content, Integer> {
}
