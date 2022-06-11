package com.pangtudy.boardapi.repository;

import com.pangtudy.boardapi.entity.Post;
import lombok.RequiredArgsConstructor;
import org.springframework.data.r2dbc.core.R2dbcEntityTemplate;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

import static org.springframework.data.relational.core.query.Criteria.where;
import static org.springframework.data.relational.core.query.Query.query;

@Repository
@RequiredArgsConstructor
public class PostCountRepositoryImpl implements PostCountRepository{
    private final R2dbcEntityTemplate r2dbcEntityTemplate;

    @Override
    public Mono<Long> findAllPostCount() {
        return r2dbcEntityTemplate.select(Post.class).count();
    }

    @Override
    public Mono<Long> findPostByCategoryIdCount(Integer categoryId) {
        return r2dbcEntityTemplate.select(Post.class)
                .matching(query(where("category_id").is(categoryId))).count();
    }

    @Override
    public Mono<Long> findPostByWriterCount(Integer categoryId, Integer writerId) {
        if (categoryId != 0) return r2dbcEntityTemplate.select(Post.class)
                .matching(query(where("writer_id").is(writerId)
                        .and(where("category_id").is(categoryId)))).count();
        else return r2dbcEntityTemplate.select(Post.class)
                .matching(query(where("writer_id").like(writerId))).count();
    }

    @Override
    public Mono<Long> findPostByTitleContainsCount(Integer categoryId, String title) {
        if (categoryId != 0) return r2dbcEntityTemplate.select(Post.class)
                .matching(query(where("title").like("%" + title + "%")
                        .and(where("category_id").is(categoryId)))).count();
        else return r2dbcEntityTemplate.select(Post.class)
                .matching(query(where("title").like("%" + title + "%"))).count();
    }

    @Override
    public Mono<Long> findPostByTitleAndContentsContainsCount(Integer categoryId, String contents) {
        if (categoryId != 0) return r2dbcEntityTemplate.select(Post.class)
                .matching(query(where("title").like("%" + contents + "%")
                        .and(where("category_id").is(categoryId)))).count();
        else return r2dbcEntityTemplate.select(Post.class)
                .matching(query(where("title").like("%" + contents + "%")
                        .or("contents").like("%" + contents + "%"))).count();
    }

    @Override
    public Mono<Long> findPostByTagContainsCount(Integer categoryId, String tag) {
        if (categoryId != 0) return r2dbcEntityTemplate.select(Post.class)
                .matching(query(where("tags").like("%" + tag + "%")
                        .and(where("category_id").is(categoryId)))).count();
        return r2dbcEntityTemplate.select(Post.class)
                .matching(query(where("tags").like("%" + tag + "%"))).count();
    }
}
