package com.pangtudy.boardapi.handler;

import com.pangtudy.boardapi.dto.Content;
import com.pangtudy.boardapi.dto.Post;
import com.pangtudy.boardapi.repository.ContentRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.web.reactive.function.server.ServerResponse.ok;

@Slf4j
@Component
@RequiredArgsConstructor
public class ContentHandler {
/*    private final ContentRepository contentRepository;

    public Mono<ServerResponse> create(ServerRequest req) {
        Mono<Content> savedCategory = req.bodyToMono(Content.class).flatMap(content -> contentRepository.save(content));
        return ok().contentType(APPLICATION_JSON).body(BodyInserters.fromProducer(savedCategory, Content.class));
    }

    public Mono<ServerResponse> read(ServerRequest req) {
        int postId = Integer.valueOf(req.pathVariable("post_id"));
        return contentRepository.findById(postId)
                .flatMap(post -> ok().contentType(APPLICATION_JSON).bodyValue(post))
                .switchIfEmpty(ServerResponse.notFound().build());
    }

    public Mono<ServerResponse> update(ServerRequest req) {
        int postId = Integer.valueOf(req.pathVariable("post_id"));
        Mono<Content> newPost = req.bodyToMono(Content.class).map(content -> {
            content.setPostId(postId);
            return content;
        });
        Mono<Content> savedContent = newPost.flatMap(content -> contentRepository.save(content));
        return ok().contentType(APPLICATION_JSON).body(BodyInserters.fromProducer(savedContent, Content.class));
    }

    public Mono<ServerResponse> delete(ServerRequest req) {
        int postId = Integer.valueOf(req.pathVariable("post_id"));
        return ok().build(contentRepository.deleteById(postId));
    }*/
}
