package com.pangtudy.boardapi.handler;

import com.pangtudy.boardapi.dto.Comment;
import com.pangtudy.boardapi.repository.CommentRepository;
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
public class CommentHandler {
    private final CommentRepository commentRepository;

    public Mono<ServerResponse> create(ServerRequest req) {
        Mono<Comment> savedPost = req.bodyToMono(Comment.class).flatMap(comment -> commentRepository.save(comment));
        return ok().contentType(APPLICATION_JSON).body(BodyInserters.fromProducer(savedPost, Comment.class));
    }

    public Mono<ServerResponse> readAll(ServerRequest req) {
        Flux<Comment> comments = commentRepository.findAll();
        return ok().contentType(APPLICATION_JSON).body(BodyInserters.fromProducer(comments, Comment.class));
    }

    //TODO : findByPostId로 포스트 내 댓글 가져오기

    public Mono<ServerResponse> read(ServerRequest req) {
        int postId = Integer.valueOf(req.pathVariable("post_id"));
        int commentId = Integer.valueOf(req.pathVariable("comment_id"));
        //TODO : postId로 post가져오고 commentId로 comment가져오기
        return commentRepository.findById(postId)
                .flatMap(post -> ok().contentType(APPLICATION_JSON).bodyValue(post))
                .switchIfEmpty(ServerResponse.notFound().build());
    }

    public Mono<ServerResponse> update(ServerRequest req) {
        int postId = Integer.valueOf(req.pathVariable("post_id"));
        int commentId = Integer.valueOf(req.pathVariable("comment_id"));
        //TODO : postId로 post가져오고 commentId로 comment가져오기

        Mono<Comment> newComment = req.bodyToMono(Comment.class).map(comment -> {
            comment.setCommentId(postId);
            return comment;
        });
        Mono<Comment> savedComment = newComment.flatMap(post -> commentRepository.save(post));
        return ok().contentType(APPLICATION_JSON).body(BodyInserters.fromProducer(savedComment, Comment.class));
    }

    public Mono<ServerResponse> delete(ServerRequest req) {
        int postId = Integer.valueOf(req.pathVariable("post_id"));
        int commentId = Integer.valueOf(req.pathVariable("comment_id"));
        Mono<Comment> oldComment = commentRepository.findById(commentId);
        return ok().build(commentRepository.deleteById(commentId));
    }
}
