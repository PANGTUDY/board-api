package com.pangtudy.boardapi.repository;

import reactor.core.publisher.Mono;

public interface PostCountRepository {
    Mono<Long> findAllPostCount();
    Mono<Long> findPostByCategoryIdCount(Integer categoryId);
    Mono<Long> findPostByWriterCount(Integer categoryId, Integer writerId);
    Mono<Long> findPostByTitleContainsCount(Integer categoryId, String title);
    Mono<Long> findPostByTitleAndContentsContainsCount(Integer categoryId, String contents);
    Mono<Long> findPostByTagContainsCount(Integer categoryId, String tag);
}
