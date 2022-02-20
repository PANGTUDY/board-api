package com.pangtudy.boardapi.repository;

import com.pangtudy.boardapi.dto.Comment;
import com.pangtudy.boardapi.dto.Post;
import lombok.RequiredArgsConstructor;
import org.springframework.data.r2dbc.core.R2dbcEntityTemplate;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

import java.time.LocalDateTime;
import java.util.stream.Collectors;

@Repository
@RequiredArgsConstructor
public class CustomPostRepositoryImpl implements CustomPostRepository {
    private final R2dbcEntityTemplate r2dbcEntityTemplate;

    @Override
    public Flux<Post> findByIdWithComments(Integer id) {
        String query =
                "SELECT " +
                        "p.post_id, p.category_id, p.tags, p.title, p.contents, p.date, p.writer, p.likes, " +
                        "c.comment_id, c.post_id, c.follow_id, c.writer, c.contents, c.date, " +
                        "FROM post as p " +
                        "LEFT OUTER JOIN comment as c " +
                        "ON p.post_id = c.post_id " +
                        "WHERE p.post_id = :id";

        return r2dbcEntityTemplate.getDatabaseClient().sql(query)
                .bind("id", id)
                .fetch()
                .all()
                .bufferUntilChanged(result -> result.get("post_id"))
                .map(rows ->
                        Post.builder()
                                .postId((Integer) rows.get(0).get("post_id"))
                                .categoryId((Integer) rows.get(0).get("category_id"))
                                .tags(String.valueOf(rows.get(0).get("tags")))
                                .title(String.valueOf(rows.get(0).get("title")))
                                .contents(String.valueOf(rows.get(0).get("contents")))
                                .date((LocalDateTime) rows.get(0).get("date"))
                                .writer(String.valueOf(rows.get(0).get("writer")))
                                .likes((Integer) rows.get(0).get("likes"))
                                .comments(
                                        rows.stream()
                                                .map(row -> Comment.builder()
                                                        .commentId((Integer) row.get("comment_id"))
                                                        .postId((Integer) row.get("post_id"))
                                                        .followId((Integer) row.get("follow_id"))
                                                        .writer((String) row.get("writer"))
                                                        .contents((String) row.get("contents"))
                                                        .date((LocalDateTime) row.get("date"))
                                                        .build())
                                                .collect(Collectors.toList())
                                )
                                .build()
                );
    }
}
