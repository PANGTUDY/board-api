package com.pangtudy.boardapi.repository;

import com.pangtudy.boardapi.dto.File;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;

public interface FileRepository extends ReactiveCrudRepository<File, Integer> {
}

