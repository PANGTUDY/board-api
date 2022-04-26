package com.pangtudy.boardapi.router;

import com.pangtudy.boardapi.entity.Comment;
import com.pangtudy.boardapi.dto.InputComment;
import com.pangtudy.boardapi.handler.CommentHandler;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springdoc.core.annotations.RouterOperation;
import org.springdoc.core.annotations.RouterOperations;
import org.springframework.context.annotation.Bean;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RequestPredicates.*;

@Slf4j
@Component
@RequiredArgsConstructor
public class CommentRouter {
    private final CommentHandler commentHandler;

    @RouterOperations({
            @RouterOperation(path = "/board/posts/{post_id}/comments/{comment_id}", produces = {
                    MediaType.APPLICATION_JSON_VALUE}, beanClass = CommentHandler.class,
                    method = RequestMethod.GET, beanMethod = "read",
                    operation = @Operation(summary = "특정 댓글 조회", operationId = "getComment",
                            responses = {
                                    @ApiResponse(responseCode = "200", description = "successful operation",
                                            content = @Content(schema = @Schema(implementation = Comment.class))),
                                    @ApiResponse(responseCode = "400", description = "Invalid Comment details supplied")},
                            parameters = {
                                    @Parameter(in = ParameterIn.PATH, name = "post_id"),
                                    @Parameter(in = ParameterIn.PATH, name = "comment_id")}
                    )),
            @RouterOperation(path = "/board/posts/{post_id}/comments/{comment_id}", produces = {
                    MediaType.APPLICATION_JSON_VALUE},
                    method = RequestMethod.PATCH, beanClass = CommentHandler.class, beanMethod = "update",
                    operation = @Operation(summary = "댓글 수정", operationId = "updateComment",
                            responses = {
                                    @ApiResponse(responseCode = "200", description = "successful operation",
                                            content = @Content(schema = @Schema(implementation = Comment.class))),
                                    @ApiResponse(responseCode = "400", description = "Invalid Comment ID supplied"),
                                    @ApiResponse(responseCode = "404", description = "Comment not found")},
                            parameters = {
                                    @Parameter(in = ParameterIn.PATH, name = "post_id"),
                                    @Parameter(in = ParameterIn.PATH, name = "comment_id")},
                            requestBody = @RequestBody(content = @Content(schema = @Schema(implementation = InputComment.class))))
            ),
            @RouterOperation(path = "/board/posts/{post_id}/comments/{comment_id}", produces = {
                    MediaType.APPLICATION_JSON_VALUE},
                    method = RequestMethod.DELETE, beanClass = CommentHandler.class, beanMethod = "delete",
                    operation = @Operation(summary = "댓글 삭제", operationId = "deleteComment",
                            responses = {
                                    @ApiResponse(responseCode = "200", description = "successful operation", content = @Content(mediaType = "Boolean")),
                                    @ApiResponse(responseCode = "400", description = "Invalid Comment ID supplied"),
                                    @ApiResponse(responseCode = "404", description = "Comment not found")},
                            parameters = {
                                    @Parameter(in = ParameterIn.PATH, name = "post_id"),
                                    @Parameter(in = ParameterIn.PATH, name = "comment_id")}
                    )),
            @RouterOperation(path = "/board/posts/{post_id}/comments", produces = {
                    MediaType.APPLICATION_JSON_VALUE},
                    method = RequestMethod.POST, beanClass = CommentHandler.class, beanMethod = "create",
                    operation = @Operation(summary = "댓글 작성", operationId = "insertComment",
                            responses = {
                                    @ApiResponse(responseCode = "200", description = "successful operation",
                                            content = @Content(schema = @Schema(implementation = Comment.class))),
                                    @ApiResponse(responseCode = "400", description = "Invalid Comment details supplied")},
                            parameters = {
                                    @Parameter(in = ParameterIn.PATH, name = "post_id")},
                            requestBody = @RequestBody(content = @Content(schema = @Schema(implementation = InputComment.class)))
                    )),
            @RouterOperation(path = "/board/posts/{post_id}/comments", produces = {
                    MediaType.APPLICATION_JSON_VALUE},
                    beanClass = CommentHandler.class,
                    method = RequestMethod.GET, beanMethod = "readAll",
                    operation = @Operation(summary = "게시글 댓글 조회", operationId = "getComments",
                            responses = {
                                    @ApiResponse(responseCode = "200", description = "successful operation",
                                            content = @Content(schema = @Schema(implementation = Comment.class))),
                                    @ApiResponse(responseCode = "400", description = "Invalid Comment details supplied")},
                            parameters = {
                                    @Parameter(in = ParameterIn.PATH, name = "post_id")}
                    ))
    })
    @Bean
    public RouterFunction<ServerResponse> commentRoutes() {
        return RouterFunctions
                .route(GET("/board/posts/{post_id}/comments/{comment_id}"), commentHandler::read)
                .andRoute(PATCH("/board/posts/{post_id}/comments/{comment_id}"), commentHandler::update)
                .andRoute(DELETE("/board/posts/{post_id}/comments/{comment_id}"), commentHandler::delete)
                .andRoute(POST("/board/posts/{post_id}/comments"), commentHandler::create)
                .andRoute(GET("/board/posts/{post_id}/comments"), commentHandler::readAll);
    }
}
