package com.pangtudy.boardapi.handler;

import com.pangtudy.boardapi.dto.OutputComment;
import com.pangtudy.boardapi.dto.User;
import com.pangtudy.boardapi.entity.Comment;
import com.pangtudy.boardapi.dto.InputComment;
import com.pangtudy.boardapi.repository.CommentRepository;
import com.pangtudy.boardapi.repository.UserRepository;
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
    private final UserRepository userRepository;

    public Mono<ServerResponse> create(ServerRequest req) {
        int postId = Integer.parseInt(req.pathVariable("post_id"));
        Mono<InputComment> newComment = req.bodyToMono(InputComment.class);
        Mono<Comment> savedComment = newComment
                .flatMap(value -> Mono.just(Comment.builder()
                        .writerId(value.getWriterId())
                        .contents(value.getContents())
                        .date(value.getDate())
                        .postId(postId)
                        .build()
                ))
                .flatMap(comment -> commentRepository.save(comment));

        Mono<OutputComment> outputComment = savedComment
                .flatMap(value -> userRepository.getUser(value.getWriterId())
                        .flatMap(user ->
                                Mono.just(OutputComment.builder()
                                        .commentId(value.getCommentId())
                                        .writerId(value.getWriterId())
                                        .contents(value.getContents())
                                        .date(value.getDate())
                                        .writerName(user.getName())
                                        .build())));

        return ok().contentType(APPLICATION_JSON).body(BodyInserters.fromProducer(outputComment, OutputComment.class));
    }

    public Mono<ServerResponse> readAll(ServerRequest req) {
        int postId = Integer.parseInt(req.pathVariable("post_id"));
        Flux<Comment> comments = commentRepository.findCommentByPostId(postId);

        Flux<OutputComment> outputComment = comments
                .flatMap(value -> userRepository.getUser(value.getWriterId())
                        .flatMap(user ->
                                Mono.just(OutputComment.builder()
                                        .commentId(value.getCommentId())
                                        .writerId(value.getWriterId())
                                        .contents(value.getContents())
                                        .date(value.getDate())
                                        .writerName(user.getName())
                                        .build())));

        return ok().contentType(APPLICATION_JSON).body(BodyInserters.fromProducer(outputComment, OutputComment.class));
    }

    public Mono<ServerResponse> read(ServerRequest req) {
        int postId = Integer.parseInt(req.pathVariable("post_id"));
        int commentId = Integer.parseInt(req.pathVariable("comment_id"));

        //TODO : 특정 댓글 조회
        return commentRepository.findCommentByPostIdAndCommentId(postId, commentId)
                .flatMap(comment -> userRepository.getUser(comment.getWriterId())
                        .flatMap(user ->
                                Mono.just(OutputComment.builder()
                                        .commentId(comment.getCommentId())
                                        .writerId(comment.getWriterId())
                                        .contents(comment.getContents())
                                        .date(comment.getDate())
                                        .writerName(user.getName())
                                        .build()))
                        .flatMap(outputComment ->
                                ok().contentType(APPLICATION_JSON)
                                        .body(BodyInserters.fromProducer(outputComment, OutputComment.class)))
                        .switchIfEmpty(ServerResponse.notFound().build()));
    }

    public Mono<ServerResponse> update(ServerRequest req) {
        int postId = Integer.parseInt(req.pathVariable("post_id"));
        int commentId = Integer.parseInt(req.pathVariable("comment_id"));
        Mono<InputComment> newComment = req.bodyToMono(InputComment.class);

        Mono<Comment> oldComment = commentRepository.findCommentByPostIdAndCommentId(postId, commentId);
        Mono<Comment> savedComment = newComment
                .flatMap(value -> Mono.just(Comment.builder()
                        .writerId(value.getWriterId())
                        .contents(value.getContents())
                        .date(value.getDate())
                        .postId(postId)
                        .commentId(commentId)
                        .build()
                ))
                .flatMap(comment -> commentRepository.save(comment));
        return ok().contentType(APPLICATION_JSON).body(BodyInserters.fromProducer(savedComment, OutputComment.class));
    }

    public Mono<ServerResponse> delete(ServerRequest req) {
        int postId = Integer.parseInt(req.pathVariable("post_id"));
        int commentId = Integer.parseInt(req.pathVariable("comment_id"));
        return ok().build(commentRepository.deleteById(commentId));
    }
}
