package com.pangtudy.boardapi.handler;

import com.pangtudy.boardapi.dto.InputPost;
import com.pangtudy.boardapi.dto.Post;
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

import java.util.Optional;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.web.reactive.function.server.ServerResponse.ok;

@Slf4j
@Component
@RequiredArgsConstructor
@Tag(name = "post", description = "게시글 API")
public class PostHandler {
    private final PostRepository postRepository;

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
                        .build()
                ))
                .flatMap(category -> postRepository.save(category));

        return ok().contentType(APPLICATION_JSON).body(BodyInserters.fromProducer(savedPost, Post.class));
    }

    public Mono<ServerResponse> readAll(ServerRequest req) {
        Optional<String> categoryId = req.queryParam("category_id");
        Flux<Post> posts;
        Integer categoryNum = categoryId.map(Integer::valueOf).orElse(0);
        if (categoryNum == 0) posts = postRepository.findAll();
        else posts = postRepository.findPostByCategoryId(categoryNum);
        return ok().contentType(APPLICATION_JSON).body(BodyInserters.fromProducer(posts, Post.class));
    }

    public Mono<ServerResponse> read(ServerRequest req) {
        int postId = Integer.valueOf(req.pathVariable("post_id"));
        Flux<Post> posts = postRepository.findByIdWithComments(postId);
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
                        //TODO : likes 설정
                        .build()))
                .flatMap(post -> postRepository.save(post));
        return ok().contentType(APPLICATION_JSON).body(BodyInserters.fromProducer(updatedPost, Post.class));
    }

    public Mono<ServerResponse> delete(ServerRequest req) {
        int postId = Integer.valueOf(req.pathVariable("post_id"));
        Mono<Post> oldPost = postRepository.findById(postId);
        return ok().build(postRepository.deleteById(postId));
    }
}
