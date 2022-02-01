package com.pangtudy.boardapi.handler;

import com.pangtudy.boardapi.dto.Post;
import com.pangtudy.boardapi.repository.PostRepository;
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
public class PostHandler {
    private final PostRepository postRepository;

    public Mono<ServerResponse> create(ServerRequest req) {
        Mono<Post> savedPost = req.bodyToMono(Post.class).flatMap(post -> postRepository.save(post));
        return ok().contentType(APPLICATION_JSON).body(BodyInserters.fromProducer(savedPost, Post.class));
    }

    public Mono<ServerResponse> readAll(ServerRequest req) {
        Optional<String> categoryId = req.queryParam("category_id");
        Flux<Post> posts;
        Integer categoryNum= categoryId.map(Integer::valueOf).orElse(0);
        if (categoryNum==0) posts = postRepository.findAll();
        else posts = postRepository.findPostByCategoryId(categoryNum);
        return ok().contentType(APPLICATION_JSON).body(BodyInserters.fromProducer(posts, Post.class));
    }

    public Mono<ServerResponse> read(ServerRequest req) {
        int postId = Integer.valueOf(req.pathVariable("post_id"));
        return postRepository.findById(postId)
                //TODO : Content, File, Tag, Comment에서 값 가져와서 세팅하기
                //.map(post->{post.setContent("GET CONTENT");return post;})
                //.map(post->{post.setContent(fileRepository.findByPostId(postId));return post;})
                .flatMap(post -> ok().contentType(APPLICATION_JSON).bodyValue(post))
                .switchIfEmpty(ServerResponse.notFound().build());
    }

    public Mono<ServerResponse> update(ServerRequest req) {
        int postId = Integer.valueOf(req.pathVariable("post_id"));
        Mono<Post> oldPost = postRepository.findById(postId);
        Mono<Post> updatedPost = req.bodyToMono(Post.class).map(post -> {
            post.setPostId(postId);
            return post;
        }).flatMap(post -> postRepository.save(post));
        return ok().contentType(APPLICATION_JSON).body(BodyInserters.fromProducer(updatedPost, Post.class));
    }

    public Mono<ServerResponse> delete(ServerRequest req) {
        int postId = Integer.valueOf(req.pathVariable("post_id"));
        Mono<Post> oldPost = postRepository.findById(postId);
        return ok().build(postRepository.deleteById(postId));
    }
}
