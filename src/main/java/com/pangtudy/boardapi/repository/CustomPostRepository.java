package com.pangtudy.boardapi.repository;

import com.pangtudy.boardapi.dto.Post;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface CustomPostRepository {
    Mono<Post> findByIdWithComments(Integer id);
    Mono<Integer> updatePostLike(Integer id, Integer likes);
    Flux<Post> findPostByWriter(Integer categoryId, String writer);
    Flux<Post> findPostByTitleContains(Integer categoryId, String title);
    Flux<Post> findPostByTitleAndContentsContains(Integer categoryId, String contents);
    Flux<Post> findPostByTagContains(Integer categoryId, String tag);
    Flux<Post> findAdjacentPosts(Integer categoryId, Integer postId);
}
