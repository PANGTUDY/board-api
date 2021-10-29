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
        Flux<Post> posts = postRepository.findAll();
        return ok().contentType(APPLICATION_JSON).body(BodyInserters.fromProducer(posts, Post.class));
    }

    public Mono<ServerResponse> read(ServerRequest req) {
        int postId = Integer.valueOf(req.pathVariable("post_id"));
        return postRepository.findById(postId)
                .flatMap(post -> ok().contentType(APPLICATION_JSON).bodyValue(post))
                .switchIfEmpty(ServerResponse.notFound().build());
    }

    public Mono<ServerResponse> update(ServerRequest req) {
        int postId = Integer.valueOf(req.pathVariable("post_id"));
        //TODO : postId에 해당하는 post가 없을 때
        //Mono<Post> beforePost = postRepository.findById(postId);
        Mono<Post> newPost = req.bodyToMono(Post.class).map(post -> {
            post.setPostId(postId);
            return post;
        });
        Mono<Post> savedPost = newPost.flatMap(post -> postRepository.save(post));
        return ok().contentType(APPLICATION_JSON).body(BodyInserters.fromProducer(savedPost, Post.class));
    }

    public Mono<ServerResponse> delete(ServerRequest req) {
        int postId = Integer.valueOf(req.pathVariable("post_id"));
        //TODO : postId에 해당하는 post가 없을 때
        //return read(req).switchIfEmpty(Mono.error(new Exception("Post not found"))).flatMap(post -> postRepository.delete((Post) post));
        return ok().build(postRepository.deleteById(postId));
    }
}