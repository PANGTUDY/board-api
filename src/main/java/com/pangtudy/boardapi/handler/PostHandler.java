package com.pangtudy.boardapi.handler;

import com.pangtudy.boardapi.dto.InputPost;
import com.pangtudy.boardapi.dto.InputUser;
import com.pangtudy.boardapi.dto.PageSearchResult;
import com.pangtudy.boardapi.entity.Likes;
import com.pangtudy.boardapi.entity.Post;
import com.pangtudy.boardapi.repository.LikesRepository;
import com.pangtudy.boardapi.repository.PostRepository;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.*;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.web.reactive.function.server.ServerResponse.ok;

@Slf4j
@Component
@RequiredArgsConstructor
@Tag(name = "post", description = "게시글 API")
public class PostHandler {
    private final PostRepository postRepository;
    private final LikesRepository likesRepository;
    public static final Long PAGE_POST_COUNT = 10L;

    public Mono<ServerResponse> create(ServerRequest req) {
        Mono<InputPost> newPost = req.bodyToMono(InputPost.class);
        Mono<Post> savedPost = newPost
                .flatMap(value -> Mono.just(Post.builder()
                        .categoryId(value.getCategoryId())
                        .tags(value.getTags())
                        .title(value.getTitle())
                        .contents(value.getContents())
                        .date(value.getDate())
                        .writerId(value.getWriterId())
                        .likes(0)
                        .build()
                ))
                .flatMap(post -> postRepository.save(post));

        return ok().contentType(APPLICATION_JSON).body(BodyInserters.fromProducer(savedPost, Post.class));
    }

    public Mono<ServerResponse> readAll(ServerRequest req) {
        Optional<String> pageNumString = req.queryParam("page_num");
        Optional<String> categoryId = req.queryParam("category_id");
        Optional<String> writerId = req.queryParam("writer_id");
        Optional<String> title = req.queryParam("title");
        Optional<String> contents = req.queryParam("contents");
        Optional<String> tag = req.queryParam("tag");
        Flux<Post> posts;
        Integer pageNum = pageNumString.map(Integer::valueOf).orElse(1);
        Integer categoryNum = categoryId.map(Integer::valueOf).orElse(0);
        Integer writerNum = writerId.map(Integer::valueOf).orElse(0);
        String titleString = title.map(String::valueOf).orElse("");
        String contentsString = contents.map(String::valueOf).orElse("");
        String tagString = tag.map(String::valueOf).orElse("");

        Long offset = (pageNum - 1) * PAGE_POST_COUNT;
        Mono<Long> resultPostCount;

        if (writerNum != 0) {
            posts = postRepository.findPostByWriter(offset, categoryNum, writerNum);
            resultPostCount = postRepository.findPostByWriterCount(categoryNum, writerNum);
        } else if (!titleString.equals("")) {
            posts = postRepository.findPostByTitleContains(offset, categoryNum, titleString);
            resultPostCount = postRepository.findPostByTitleContainsCount(categoryNum, titleString);
        } else if (!contentsString.equals("")) {
            posts = postRepository.findPostByTitleAndContentsContains(offset, categoryNum, contentsString);
            resultPostCount = postRepository.findPostByTitleAndContentsContainsCount(categoryNum, contentsString);
        } else if (!tagString.equals("")) {
            posts = postRepository.findPostByTagContains(offset, categoryNum, tagString);
            resultPostCount = postRepository.findPostByTagContainsCount(categoryNum, tagString);
        } else if (categoryNum != 0) {
            posts = postRepository.findPostByCategoryId(offset, categoryNum);
            resultPostCount = postRepository.findPostByCategoryIdCount(categoryNum);
        } else {
            posts = postRepository.findAllPost(offset);
            resultPostCount = postRepository.findAllPostCount();
        }

        posts = posts.sort(Comparator.comparingInt(Post::getPostId).reversed());

        return ok().contentType(APPLICATION_JSON).body(BodyInserters.fromProducer(posts.collectList().zipWith(resultPostCount)
                .map(tuples -> {
                    List<Post> postList = tuples.getT1();
                    long totalPageNum = (tuples.getT2() / PAGE_POST_COUNT) + 1;

                    return PageSearchResult.builder()
                            .posts(postList)
                            .currPageNum(pageNum.longValue())
                            .totalPageNum(totalPageNum)
                            .build();
                }), PageSearchResult.class));
    }

    public Mono<ServerResponse> read(ServerRequest req) {
        int postId = Integer.parseInt(req.pathVariable("post_id"));
        Mono<Post> posts = postRepository.findByIdWithComments(postId);
        return ok().contentType(APPLICATION_JSON).body(BodyInserters.fromProducer(posts, Post.class));
    }

    public Mono<ServerResponse> readAdjacent(ServerRequest req) {
        int categoryId = Integer.parseInt(req.pathVariable("category_id"));
        int postId = Integer.parseInt(req.pathVariable("post_id"));
        Flux<Post> posts = postRepository.findAdjacentPosts(categoryId, postId);
        posts = posts.sort(Comparator.comparingInt(Post::getPostId).reversed());
        return ok().contentType(APPLICATION_JSON).body(BodyInserters.fromProducer(posts, Post.class));
    }

    public Mono<ServerResponse> update(ServerRequest req) {
        int postId = Integer.parseInt(req.pathVariable("post_id"));
        Mono<InputPost> newPost = req.bodyToMono(InputPost.class);
        Mono<Post> oldPost = postRepository.findById(postId);
        Mono<Post> updatedPost = newPost
                .flatMap(value -> Mono.just(Post.builder()
                        .postId(postId)
                        .categoryId(value.getCategoryId())
                        .tags(value.getTags())
                        .title(value.getTitle())
                        .contents(value.getContents())
                        .date(value.getDate())
                        .writerId(value.getWriterId())
                        .build()))
                .flatMap(post -> postRepository.save(post));
        return ok().contentType(APPLICATION_JSON).body(BodyInserters.fromProducer(updatedPost, Post.class));
    }

    public Mono<ServerResponse> updateLikes(ServerRequest req) {
        int postId = Integer.parseInt(req.pathVariable("post_id"));
        Mono<InputUser> user = req.bodyToMono(InputUser.class);

        Mono<Long> result = user.flatMap(inputUser -> likesRepository.findByPostId(postId)
                .filter(post -> post.getUserId().equals(inputUser.getUserId()))
                .flatMap(existInputUser -> likesRepository.findPostLikeCount(postId)
                        .flatMap(like -> postRepository.updatePostLike(postId, like.intValue() - 1))
                        .flatMap(like -> likesRepository.deleteByPostIdAndUserId(postId, existInputUser.getUserId()))
                        .flatMap(a -> likesRepository.findPostLikeCount(postId))
                        .cast(Long.class))
                .switchIfEmpty(likesRepository.findPostLikeCount(postId)
                        .flatMap(like -> postRepository.updatePostLike(postId, like.intValue() + 1))
                        .flatMap(rowCnt -> likesRepository.insertUserId(postId, inputUser.getUserId()))
                        .flatMap(a -> likesRepository.findPostLikeCount(postId))
                )
                .single());
        return ok().contentType(APPLICATION_JSON).body(BodyInserters.fromProducer(result, Likes.class));
    }

    public Mono<ServerResponse> selectUsersLikePost(ServerRequest req) {
        int postId = Integer.parseInt(req.pathVariable("post_id"));

        Flux<Likes> likesFlux = likesRepository.findByPostId(postId);
        likesFlux = likesFlux.sort(Comparator.comparingInt(Likes::getUserId));
        Flux<Integer> userList = likesFlux.map(Likes::getUserId);
        return ok().contentType(APPLICATION_JSON).body(BodyInserters.fromProducer(userList, Integer.class));
    }

    public Mono<ServerResponse> delete(ServerRequest req) {
        int postId = Integer.parseInt(req.pathVariable("post_id"));
        Mono<Post> oldPost = postRepository.findById(postId);
        return ok().build(postRepository.deleteById(postId));
    }
}
