package com.pangtudy.boardapi.repository;

import com.pangtudy.boardapi.entity.Category;
import com.pangtudy.boardapi.entity.Comment;
import com.pangtudy.boardapi.entity.Post;
import lombok.RequiredArgsConstructor;
import org.springframework.data.r2dbc.core.R2dbcEntityTemplate;
import org.springframework.data.relational.core.query.Query;
import org.springframework.data.relational.core.query.Update;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.stream.Collectors;

import static org.springframework.data.domain.Sort.Order.asc;
import static org.springframework.data.domain.Sort.Order.desc;
import static org.springframework.data.domain.Sort.by;
import static org.springframework.data.relational.core.query.Criteria.where;
import static org.springframework.data.relational.core.query.Query.query;

@Repository
@RequiredArgsConstructor
public class CustomPostRepositoryImpl implements CustomPostRepository {
    private final R2dbcEntityTemplate r2dbcEntityTemplate;
    public static final Integer SELECT_LIMIT = 10;

    @Override
    public Mono<Post> findByIdWithComments(Integer id) {
        String query =
                "SELECT " +
                        "p.post_id as p_post_id, p.category_id, p.tags, p.title, p.contents as p_contents, p.date as p_date, p.writer_id as p_writer_id, p.likes, " +
                        "c.comment_id, c.post_id, c.writer_id, c.contents, c.date, " +
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
                                .writerId((Integer) rows.get(0).get("p_writer_id"))
                                .likes((Integer) rows.get(0).get("likes"))
                                .comments(
                                        rows.stream()
                                                .map(row -> Comment.builder()
                                                        .commentId((Integer) row.get("comment_id"))
                                                        .postId((Integer) row.get("post_id"))
                                                        .writerId((Integer) row.get("writer_id"))
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
                            "p.post_id as p_post_id, p.category_id, p.tags, p.title, p.contents as p_contents, p.date as p_date, p.writer_id as p_writer_id, p.likes, " +
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
    public Mono<Integer> updatePostLike(Integer postId, Integer likes) {
        return r2dbcEntityTemplate.update(Post.class)
                .matching(Query.query(where("post_id").is(postId))).apply(Update.update("likes", likes));
    }

    @Override
    public Flux<Post> findAllPost(Long offset) {
        return r2dbcEntityTemplate.select(Post.class)
                .matching(Query.empty().offset(offset).limit(SELECT_LIMIT)).all();
    }

    @Override
    public Flux<Post> findPostByCategoryId(Long offset, Integer categoryId) {
        return r2dbcEntityTemplate.select(Post.class)
                .matching(query(where("category_id").is(categoryId))
                        .offset(offset).limit(SELECT_LIMIT)).all();
    }

    @Override
    public Flux<Post> findPostByWriter(Long offset, Integer categoryId, Integer writerId) {
        if (categoryId != 0) return r2dbcEntityTemplate.select(Post.class)
                .matching(query(where("writer_id").is(writerId)
                        .and(where("category_id").is(categoryId)))
                        .offset(offset).limit(SELECT_LIMIT)).all();
        else return r2dbcEntityTemplate.select(Post.class)
                .matching(query(where("writer_id").is(writerId))
                        .offset(offset).limit(SELECT_LIMIT)).all();
    }

    @Override
    public Flux<Post> findPostByTitleContains(Long offset, Integer categoryId, String title) {
        if (categoryId != 0) return r2dbcEntityTemplate.select(Post.class)
                .matching(query(where("title").like("%" + title + "%")
                        .and(where("category_id").is(categoryId)))
                        .offset(offset).limit(SELECT_LIMIT)).all();
        else return r2dbcEntityTemplate.select(Post.class)
                .matching(query(where("title").like("%" + title + "%"))
                        .offset(offset).limit(SELECT_LIMIT)).all();
    }

    @Override
    public Flux<Post> findPostByTitleAndContentsContains(Long offset, Integer categoryId, String contents) {
        if (categoryId != 0) return r2dbcEntityTemplate.select(Post.class)
                .matching(query(where("title").like("%" + contents + "%")
                        .and(where("category_id").is(categoryId)))
                        .offset(offset).limit(SELECT_LIMIT)).all();
        else return r2dbcEntityTemplate.select(Post.class)
                .matching(query(where("title").like("%" + contents + "%")
                        .or("contents").like("%" + contents + "%"))
                        .offset(offset).limit(SELECT_LIMIT)).all();
    }

    @Override
    public Flux<Post> findPostByTagContains(Long offset, Integer categoryId, String tag) {
        if (categoryId != 0) return r2dbcEntityTemplate.select(Post.class)
                .matching(query(where("tags").like("%" + tag + "%")
                        .and(where("category_id").is(categoryId)))
                        .offset(offset).limit(SELECT_LIMIT)).all();
        return r2dbcEntityTemplate.select(Post.class)
                .matching(query(where("tags").like("%" + tag + "%"))
                        .offset(offset).limit(SELECT_LIMIT)).all();
    }

    public Flux<Post> findAdjacentPosts(Integer categoryId, Integer postId) {
        //현재 게시글 조회
        Flux<Post> currentPost = r2dbcEntityTemplate.select(Post.class)
                .matching(query(where("post_id").is(postId)
                        .and(where("category_id").is(categoryId)))).all();

        Flux<Post> beforePosts = findBeforePosts(categoryId, postId, 1);
        Flux<Post> result = beforePosts.flatMap(existBeforePost -> {
            Flux<Post> afterPosts = findAfterPosts(categoryId, postId, 1);
            return afterPosts.flatMap(existAfterPost -> {
                return Flux.concat(beforePosts, currentPost, findAfterPosts(categoryId, postId, 1));
            }).switchIfEmpty(Flux.concat(findBeforePosts(categoryId, postId, 2).takeLast(1), findBeforePosts(categoryId, postId, 2).take(1), currentPost));
        }).switchIfEmpty(Flux.concat(currentPost, findAfterPosts(categoryId, postId, 2)));

        return result;
    }

    private Flux<Post> findBeforePosts(Integer categoryId, Integer postId, Integer count) {
        return r2dbcEntityTemplate.select(Post.class)
                .matching(Query.query(where("post_id").lessThan(postId)
                        .and(where("category_id")
                                .is(categoryId)))
                        .sort(by(desc("post_id"))).limit(count)).all();
    }

    private Flux<Post> findAfterPosts(Integer categoryId, Integer postId, Integer count) {
        //이후 게시글 조회
        return r2dbcEntityTemplate.select(Post.class)
                .matching(Query.query(where("post_id").greaterThan(postId)
                        .and(where("category_id")
                                .is(categoryId)))
                        .sort(by(asc("post_id"))).limit(count)).all();
    }
}
