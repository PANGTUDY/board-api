package com.pangtudy.boardapi.repository;

import com.pangtudy.boardapi.dto.Likes;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

public interface LikesRepository extends ReactiveCrudRepository<Likes, Integer>, CustomLikesRepository{
}
