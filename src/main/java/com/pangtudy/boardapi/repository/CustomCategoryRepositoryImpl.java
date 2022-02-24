package com.pangtudy.boardapi.repository;

import com.pangtudy.boardapi.dto.Category;
import com.pangtudy.boardapi.dto.Post;
import lombok.RequiredArgsConstructor;
import org.springframework.data.r2dbc.core.R2dbcEntityTemplate;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;
import java.util.stream.Collectors;

@Repository
@RequiredArgsConstructor
public class CustomCategoryRepositoryImpl implements CustomCategoryRepository {

    private final R2dbcEntityTemplate r2dbcEntityTemplate;

    @Override
    public Mono<Category> findByIdWithPosts(Integer id) {
        String query =
                "SELECT " +
                "c.category_id as c_category_id, c.category_name, " +
                "p.post_id, p.category_id, p.tags, p.title, p.contents, p.date, p.writer, p.likes, " +
                "FROM category as c " +
                "LEFT OUTER JOIN post as p " +
                "ON c.category_id = p.category_id " +
                "WHERE c.category_id = :id";

        return r2dbcEntityTemplate.getDatabaseClient().sql(query)
                .bind("id",id)
                .fetch()
                .all()
                .bufferUntilChanged(result -> result.get("c_category_id"))
                .map(rows ->
                        Category.builder()
                                .categoryId((Integer) rows.get(0).get("c_category_id"))
                                .categoryName(String.valueOf(rows.get(0).get("category_name")))
                                .posts(
                                        rows.stream()
                                                .map(row -> Post.builder()
                                                        .postId((Integer) row.get("post_id"))
                                                        .categoryId((Integer) row.get("category_id"))
                                                        .tags((String) row.get("tags"))
                                                        .title((String) row.get("title"))
                                                        .contents((String) row.get("contents"))
                                                        .date((LocalDateTime) row.get("date"))
                                                        .writer((String) row.get("writer"))
                                                        .likes((Integer) row.get("likes"))
                                                        .build())
                                                .collect(Collectors.toList())
                                )
                                .build()
                ).single();
    }
}
