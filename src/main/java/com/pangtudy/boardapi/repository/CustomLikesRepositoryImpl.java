package com.pangtudy.boardapi.repository;

import com.pangtudy.boardapi.entity.Likes;
import lombok.RequiredArgsConstructor;
import org.springframework.data.r2dbc.core.R2dbcEntityTemplate;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import static org.springframework.data.relational.core.query.Query.query;
import static org.springframework.data.relational.core.query.Criteria.where;

@Repository
@RequiredArgsConstructor
public class CustomLikesRepositoryImpl implements CustomLikesRepository {

    private final R2dbcEntityTemplate r2dbcEntityTemplate;

    @Override
    public Flux<Likes> findByUserId(Integer userId) {
        return r2dbcEntityTemplate.select(query(where("user_id").is(userId)), Likes.class);
    }

    @Override
    public Flux<Likes> findByPostId(Integer postId) {
        return r2dbcEntityTemplate.select(query(where("post_id").is(postId)), Likes.class);
    }

    @Override
    public Mono<Long> findPostLikeCount(Integer postId) {
        return r2dbcEntityTemplate.select(query(where("post_id").is(postId)), Likes.class).count();
    }

    @Override
    public Mono<Integer> deleteByPostIdAndUserId(Integer postId, Integer userId) {
        return r2dbcEntityTemplate.delete(query(where("post_id").is(postId).and("user_id").is(userId)), Likes.class);
    }

    @Override
    public Mono<Likes> insertUserId(Integer postId, Integer userId) {
        Likes likes= Likes.builder().userId(userId).postId(postId).build();
        return r2dbcEntityTemplate.insert(Likes.class).using(likes);
    }

}
