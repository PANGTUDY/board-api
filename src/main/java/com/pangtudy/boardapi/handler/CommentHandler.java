package com.pangtudy.boardapi.handler;

import com.pangtudy.boardapi.dto.Comment;
import com.pangtudy.boardapi.dto.InputComment;
import com.pangtudy.boardapi.dto.InputPost;
import com.pangtudy.boardapi.dto.Post;
import com.pangtudy.boardapi.repository.CommentRepository;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@Tag(name = "comment", description = "댓글 API")
public class CommentHandler {
    private final CommentRepository commentRepository;

    public Mono<ServerResponse> create(ServerRequest req) {
        int postId = Integer.valueOf(req.pathVariable("post_id"));
        Mono<InputComment> newComment = req.bodyToMono(InputComment.class);
        Mono<Comment> savedComment = newComment
                .flatMap(value -> Mono.just(Comment.builder()
                        .writer(value.getWriter())
                        .writer(value.getWriter())
                        .contents(value.getContents())
                        .date(value.getDate())
                        .postId(postId)
                        .build()
                ))
                .flatMap(comment -> commentRepository.save(comment));
        return ok().contentType(APPLICATION_JSON).body(BodyInserters.fromProducer(savedComment, Comment.class));
    }

    public Mono<ServerResponse> readAll(ServerRequest req) {
        int postId = Integer.valueOf(req.pathVariable("post_id"));
        Flux<Comment> comments = commentRepository.findCommentByPostId(postId);
        return ok().contentType(APPLICATION_JSON).body(BodyInserters.fromProducer(comments, Comment.class));
    }

    public Mono<ServerResponse> read(ServerRequest req) {
        int postId = Integer.valueOf(req.pathVariable("post_id"));
        int commentId = Integer.valueOf(req.pathVariable("comment_id"));
        return commentRepository.findCommentByPostIdAndCommentId(postId, commentId)
                .flatMap(post -> ok().contentType(APPLICATION_JSON).bodyValue(post))
                .switchIfEmpty(ServerResponse.notFound().build());
    }

    public Mono<ServerResponse> update(ServerRequest req) {
        int postId = Integer.valueOf(req.pathVariable("post_id"));
        int commentId = Integer.valueOf(req.pathVariable("comment_id"));
        Mono<InputComment> newComment = req.bodyToMono(InputComment.class);

        Mono<Comment> oldComment = commentRepository.findCommentByPostIdAndCommentId(postId, commentId);
        Mono<Comment> savedComment = newComment
                .flatMap(value -> Mono.just(Comment.builder()
                        .writer(value.getWriter())
                        .writer(value.getWriter())
                        .contents(value.getContents())
                        .date(value.getDate())
                        .postId(postId)
                        .commentId(commentId)
                        .build()
                ))
                .flatMap(comment -> commentRepository.save(comment));
        return ok().contentType(APPLICATION_JSON).body(BodyInserters.fromProducer(savedComment, Comment.class));
    }

    public Mono<ServerResponse> delete(ServerRequest req) {
        int postId = Integer.valueOf(req.pathVariable("post_id"));
        int commentId = Integer.valueOf(req.pathVariable("comment_id"));
        return ok().build(commentRepository.deleteById(commentId));
    }
}
