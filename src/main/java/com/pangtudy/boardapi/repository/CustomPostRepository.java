package com.pangtudy.boardapi.repository;

import com.pangtudy.boardapi.entity.Post;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface CustomPostRepository {
    Flux<Post> findAllPost(Long offset);
    Flux<Post> findPostByCategoryId(Long offset, Integer categoryId);
    Mono<Post> findByIdWithComments(Integer id);
    Mono<Integer> updatePostLike(Integer id, Integer likes);
    Flux<Post> findPostByWriter(Long offset, Integer categoryId, String writer);
    Flux<Post> findPostByTitleContains(Long offset, Integer categoryId, String title);
    Flux<Post> findPostByTitleAndContentsContains(Long offset, Integer categoryId, String contents);
    Flux<Post> findPostByTagContains(Long offset, Integer categoryId, String tag);
    Flux<Post> findAdjacentPosts(Integer categoryId, Integer postId);
}
