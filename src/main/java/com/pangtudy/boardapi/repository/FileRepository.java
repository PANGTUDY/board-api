package com.pangtudy.boardapi.repository;

import com.pangtudy.boardapi.entity.File;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

public interface FileRepository extends ReactiveCrudRepository<File, Integer> {
}

