package com.pangtudy.boardapi.handler;

import com.pangtudy.boardapi.dto.InputPost;
import com.pangtudy.boardapi.dto.InputUser;
import com.pangtudy.boardapi.entity.Likes;
import com.pangtudy.boardapi.entity.Post;
import com.pangtudy.boardapi.repository.LikesRepository;
import com.pangtudy.boardapi.repository.PostRepository;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
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
                        .writer(value.getWriter())
                        .likes(0)
                        .build()
                ))
                .flatMap(category -> postRepository.save(category));

        return ok().contentType(APPLICATION_JSON).body(BodyInserters.fromProducer(savedPost, Post.class));
    }

    public Mono<ServerResponse> readAll(ServerRequest req) {
        Optional<String> pageNumString = req.queryParam("page_num");
        Optional<String> categoryId = req.queryParam("category_id");
        Optional<String> writer = req.queryParam("writer");
        Optional<String> title = req.queryParam("title");
        Optional<String> contents = req.queryParam("contents");
        Optional<String> tag = req.queryParam("tag");
        Flux<Post> posts;
        Integer pageNum = pageNumString.map(Integer::valueOf).orElse(1);
        Integer categoryNum = categoryId.map(Integer::valueOf).orElse(0);
        String writerName = writer.map(String::valueOf).orElse("");
        String titleString = title.map(String::valueOf).orElse("");
        String contentsString = contents.map(String::valueOf).orElse("");
        String tagString = tag.map(String::valueOf).orElse("");

        Long offset = (pageNum - 1) * PAGE_POST_COUNT;
        Map<String, Long> map = new HashMap<>();
        Mono<Long> resultPostCount;

        if (writerName != "") {
            posts = postRepository.findPostByWriter(offset, categoryNum, writerName);
            resultPostCount = postRepository.findPostByWriterCount(categoryNum, writerName);
        } else if (titleString != "") {
            posts = postRepository.findPostByTitleContains(offset, categoryNum, titleString);
            resultPostCount = postRepository.findPostByTitleContainsCount(categoryNum, titleString);
        } else if (contentsString != "") {
            posts = postRepository.findPostByTitleAndContentsContains(offset, categoryNum, contentsString);
            resultPostCount = postRepository.findPostByTitleAndContentsContainsCount(categoryNum, contentsString);
        } else if (tagString != "") {
            posts = postRepository.findPostByTagContains(offset, categoryNum, tagString);
            resultPostCount = postRepository.findPostByTagContainsCount(categoryNum, tagString);
        } else if (categoryNum != 0) {
            posts = postRepository.findPostByCategoryId(offset, categoryNum);
            resultPostCount = postRepository.findPostByCategoryIdCount(categoryNum);
        } else {
            posts = postRepository.findAllPost(offset);
            resultPostCount = postRepository.findAllPostCount();
        }

        List<Long> subscribedResultPostCount = callUserItem(resultPostCount);
        Long totalPageNum = (subscribedResultPostCount.get(0) / PAGE_POST_COUNT) + 1;
        if (Long.valueOf(pageNum) > totalPageNum || Long.valueOf(pageNum) < 1)
            return ServerResponse.status(HttpStatus.BAD_REQUEST)
                    .contentType(MediaType.APPLICATION_JSON).bodyValue(new Exception("invalid input page number"));
        map.put("currPageNum", Long.valueOf(pageNum));
        map.put("totalPageNum", totalPageNum);
        Flux<Map<String, Long>> pageData = Flux.just(map);
        return ok().contentType(APPLICATION_JSON).body(BodyInserters.fromProducer(Flux.concat(posts, pageData), Post.class));
    }

    private List<Long> callUserItem(Mono<Long> totalResult) {
        List<Long> itemList = new ArrayList<>();
        totalResult.subscribe(res -> {
            if (res != null) {
                itemList.add(res);
            }
        });
        return itemList;
    }


    public Mono<ServerResponse> read(ServerRequest req) {
        int postId = Integer.valueOf(req.pathVariable("post_id"));
        Mono<Post> posts = postRepository.findByIdWithComments(postId);
        return ok().contentType(APPLICATION_JSON).body(BodyInserters.fromProducer(posts, Post.class));
    }

    public Mono<ServerResponse> readAdjacent(ServerRequest req) {
        int categoryId = Integer.valueOf(req.pathVariable("category_id"));
        int postId = Integer.valueOf(req.pathVariable("post_id"));
        Flux<Post> posts = postRepository.findAdjacentPosts(categoryId, postId);
        return ok().contentType(APPLICATION_JSON).body(BodyInserters.fromProducer(posts, Post.class));
    }

    public Mono<ServerResponse> update(ServerRequest req) {
        int postId = Integer.valueOf(req.pathVariable("post_id"));
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
                        .writer(value.getWriter())
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
        List<Integer> userList = new ArrayList<>();
        likesFlux.subscribe(user -> userList.add(user.getUserId()));
        return ok().contentType(APPLICATION_JSON).body(BodyInserters.fromProducer(Mono.just(userList), Likes.class));
    }

    public Mono<ServerResponse> delete(ServerRequest req) {
        int postId = Integer.valueOf(req.pathVariable("post_id"));
        Mono<Post> oldPost = postRepository.findById(postId);
        return ok().build(postRepository.deleteById(postId));
    }
}
