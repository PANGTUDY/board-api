package com.pangtudy.boardapi.repository;

import com.pangtudy.boardapi.entity.Comment;
import lombok.RequiredArgsConstructor;
import org.springframework.data.r2dbc.core.R2dbcEntityTemplate;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

import static org.springframework.data.relational.core.query.Query.query;
import static org.springframework.data.relational.core.query.Criteria.where;

@Repository
@RequiredArgsConstructor
public class CustomCommentRepositoryImpl implements CustomCommentRepository {

    private final R2dbcEntityTemplate r2dbcEntityTemplate;

    @Override
    public Mono<Comment> findCommentByPostIdAndCommentId(Integer postId, Integer commentId) {
        return r2dbcEntityTemplate.selectOne(query(where("comment_id").is(commentId).and(where("post_id").is(postId))), Comment.class);
    }

}
