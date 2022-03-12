package com.pangtudy.boardapi.repository;

import com.pangtudy.boardapi.dto.Category;
import com.pangtudy.boardapi.dto.Comment;
import com.pangtudy.boardapi.dto.Post;
import lombok.RequiredArgsConstructor;
import org.springframework.data.r2dbc.core.R2dbcEntityTemplate;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;
import java.util.stream.Collectors;

import static org.springframework.data.relational.core.query.Criteria.where;
import static org.springframework.data.relational.core.query.Query.query;

@Repository
@RequiredArgsConstructor
public class CustomPostRepositoryImpl implements CustomPostRepository {
    private final R2dbcEntityTemplate r2dbcEntityTemplate;

    @Override
    public Mono<Post> findByIdWithComments(Integer id) {
        String query =
                "SELECT " +
                        "p.post_id as p_post_id, p.category_id, p.tags, p.title, p.contents as p_contents, p.date as p_date, p.writer as p_writer, p.likes, " +
                        "c.comment_id, c.post_id, c.follow_id, c.writer, c.contents, c.date, " +
                        "FROM post as p " +
                        "LEFT OUTER JOIN comment as c " +
                        "ON p.post_id = c.post_id " +
                        "WHERE p.post_id = :id";

        return r2dbcEntityTemplate.getDatabaseClient().sql(query)
                .bind("id", id)
                .fetch()
                .all()
                .bufferUntilChanged(result -> result.get("p_post_id"))
                .map(rows ->
                        Post.builder()
                                .postId((Integer) rows.get(0).get("p_post_id"))
                                .categoryId((Integer) rows.get(0).get("category_id"))
                                .tags(String.valueOf(rows.get(0).get("tags")))
                                .title(String.valueOf(rows.get(0).get("title")))
                                .contents(String.valueOf(rows.get(0).get("p_contents")))
                                .date((LocalDateTime) rows.get(0).get("p_date"))
                                .writer(String.valueOf(rows.get(0).get("p_writer")))
                                .likes((Integer) rows.get(0).get("likes"))
                                .comments(
                                        rows.stream()
                                                .map(row -> Comment.builder()
                                                        .commentId((Integer) row.get("comment_id"))
                                                        .postId((Integer) row.get("post_id"))
                                                        .writer((String) row.get("writer"))
                                                        .contents((String) row.get("contents"))
                                                        .date((LocalDateTime) row.get("date"))
                                                        .build())
                                                .collect(Collectors.toList())
                                )
                                .build()
                )
                .map(post -> {
                    if (post.getComments().get(0).getCommentId() == null) post.setComments(null);
                    return post;
                })
                .flatMap(post -> {
                    String query2 = "SELECT " +
                            "p.post_id as p_post_id, p.category_id, p.tags, p.title, p.contents as p_contents, p.date as p_date, p.writer as p_writer, p.likes, " +
                            "c.category_id as c_category_id, c.category_name, " +
                            "FROM post as p " +
                            "LEFT OUTER JOIN category as c " +
                            "ON p.category_id = c.category_id " +
                            "WHERE p.category_id = :id";

                    return r2dbcEntityTemplate.getDatabaseClient().sql(query2)
                            .bind("id", post.getCategoryId())
                            .fetch()
                            .all()
                            .bufferUntilChanged(result -> result.get("c_category_id"))
                            .map(p -> {
                                post.setCategory(Category.builder().categoryId((Integer) p.get(0).get("c_category_id")).categoryName((String) p.get(0).get("category_name")).build());
                                return post;
                            }).single();
                }).single();
    }

    @Override
    public Flux<Post> findPostByTitleContains(String title) {
        return r2dbcEntityTemplate.select(Post.class)
                .matching(query(where("title").like("%" + title + "%"))).all();
    }

    @Override
    public Flux<Post> findPostByTitleAndContentsContains(String contents) {
        return r2dbcEntityTemplate.select(Post.class)
                .matching(query(where("title").like("%" + contents + "%").or("contents").like("%" + contents + "%"))).all();
    }
}
