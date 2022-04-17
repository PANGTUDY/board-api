package com.pangtudy.boardapi.router;

import com.pangtudy.boardapi.dto.InputPost;
import com.pangtudy.boardapi.dto.InputUser;
import com.pangtudy.boardapi.dto.Likes;
import com.pangtudy.boardapi.dto.Post;
import com.pangtudy.boardapi.handler.PostHandler;
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
public class PostRouter {
    private final PostHandler postHandler;

    @RouterOperations({
            @RouterOperation(path = "/board/posts/{post_id}", produces = {
                    MediaType.APPLICATION_JSON_VALUE},
                    beanClass = PostHandler.class, method = RequestMethod.GET, beanMethod = "read",
                    operation = @Operation(summary = "특정 게시글 조회", operationId = "getPost",
                            responses = {
                                    @ApiResponse(responseCode = "200", description = "successful operation",
                                            content = @Content(schema = @Schema(implementation = Post.class))),
                                    @ApiResponse(responseCode = "400", description = "Invalid Post details supplied")},
                            parameters = {
                                    @Parameter(in = ParameterIn.PATH, name = "post_id")}
                    )),
            @RouterOperation(path = "/board/posts/{post_id}/like", produces = {
                    MediaType.APPLICATION_JSON_VALUE},
                    beanClass = PostHandler.class, method = RequestMethod.POST, beanMethod = "updateLikes",
                    operation = @Operation(summary = "좋아요 상태 변경", operationId = "updateLikes",
                            responses = {
                                    @ApiResponse(responseCode = "200", description = "successful operation",
                                            content = @Content(schema = @Schema(implementation = Long.class))),
                                    @ApiResponse(responseCode = "400", description = "Invalid Post details supplied")},
                            parameters = {
                                    @Parameter(in = ParameterIn.PATH, name = "post_id")},
                            requestBody = @RequestBody(content = @Content(schema = @Schema(implementation = InputUser.class)))
                    )),
            @RouterOperation(path = "/board/posts/{post_id}/like", produces = {
                    MediaType.APPLICATION_JSON_VALUE},
                    beanClass = PostHandler.class, method = RequestMethod.GET, beanMethod = "selectUsersLikePost",
                    operation = @Operation(summary = "좋아요한 유저 조회", operationId = "selectUsersLikePost",
                            responses = {
                                    @ApiResponse(responseCode = "200", description = "successful operation",
                                            content = @Content(schema = @Schema(implementation = Likes.class))),
                                    @ApiResponse(responseCode = "400", description = "Invalid Post details supplied")},
                            parameters = {
                                    @Parameter(in = ParameterIn.PATH, name = "post_id")}
                    )),
            @RouterOperation(path = "/board/posts/adjacent/{category_id}/{post_id}", produces = {
                    MediaType.APPLICATION_JSON_VALUE},
                    beanClass = PostHandler.class, method = RequestMethod.GET, beanMethod = "readAdjacent",
                    operation = @Operation(summary = "앞 뒤 게시글 조회", operationId = "getAdjacentPosts",
                            responses = {
                                    @ApiResponse(responseCode = "200", description = "successful operation",
                                            content = @Content(schema = @Schema(implementation = Post.class))),
                                    @ApiResponse(responseCode = "400", description = "Invalid Post details supplied")},
                            parameters = {
                                    @Parameter(in = ParameterIn.PATH, name = "category_id"),
                                    @Parameter(in = ParameterIn.PATH, name = "post_id")}
                    )),
            @RouterOperation(path = "/board/posts/{post_id}", produces = {
                    MediaType.APPLICATION_JSON_VALUE},
                    method = RequestMethod.PATCH, beanClass = PostHandler.class, beanMethod = "update",
                    operation = @Operation(summary = "게시글 수정", operationId = "updatePost",
                            responses = {
                                    @ApiResponse(responseCode = "200", description = "successful operation",
                                            content = @Content(schema = @Schema(implementation = Post.class))),
                                    @ApiResponse(responseCode = "400", description = "Invalid Post ID supplied"),
                                    @ApiResponse(responseCode = "404", description = "Post not found")},
                            parameters = {
                                    @Parameter(in = ParameterIn.PATH, name = "post_id")},
                            requestBody = @RequestBody(content = @Content(schema = @Schema(implementation = InputPost.class))))
            ),
            @RouterOperation(path = "/board/posts/{post_id}", produces = {
                    MediaType.APPLICATION_JSON_VALUE},
                    method = RequestMethod.DELETE, beanClass = PostHandler.class, beanMethod = "delete",
                    operation = @Operation(summary = "게시글 삭제", operationId = "deletePost",
                            responses = {
                                    @ApiResponse(responseCode = "200", description = "successful operation",
                                            content = @Content(mediaType = "Boolean")),
                                    @ApiResponse(responseCode = "400", description = "Invalid Post ID supplied"),
                                    @ApiResponse(responseCode = "404", description = "Post not found")},
                            parameters = {
                                    @Parameter(in = ParameterIn.PATH, name = "post_id")}
                    )),
            @RouterOperation(path = "/board/posts", produces = {
                    MediaType.APPLICATION_JSON_VALUE},
                    method = RequestMethod.POST, beanClass = PostHandler.class, beanMethod = "create",
                    operation = @Operation(summary = "게시글 작성", operationId = "insertPost",
                            responses = {
                                    @ApiResponse(responseCode = "200", description = "successful operation",
                                            content = @Content(schema = @Schema(implementation = Post.class))),
                                    @ApiResponse(responseCode = "400", description = "Invalid Post details supplied")},
                            requestBody = @RequestBody(content = @Content(schema = @Schema(implementation = InputPost.class)))
                    )),
            @RouterOperation(path = "/board/posts", produces = {
                    MediaType.APPLICATION_JSON_VALUE}, beanClass = PostHandler.class,
                    method = RequestMethod.GET, beanMethod = "readAll",
                    operation = @Operation(summary = "게시글 조회", operationId = "getPosts",
                            responses = {
                                    @ApiResponse(responseCode = "200", description = "successful operation",
                                            content = @Content(schema = @Schema(implementation = Post.class)))},
                            parameters = {
                                    @Parameter(name = "category_id", in = ParameterIn.QUERY, schema = @Schema(type = "Integer")),
                                    @Parameter(name = "writer", in = ParameterIn.QUERY, schema = @Schema(type = "String")),
                                    @Parameter(name = "title", in = ParameterIn.QUERY, schema = @Schema(type = "String")),
                                    @Parameter(name = "contents", in = ParameterIn.QUERY, schema = @Schema(type = "String")),
                                    @Parameter(name = "tag", in = ParameterIn.QUERY, schema = @Schema(type = "String"))}
                    ))
    })
    @Bean
    public RouterFunction<ServerResponse> postRoutes() {
        return RouterFunctions
                .route(GET("/board/posts/{post_id}"), postHandler::read)
                .andRoute(POST("/board/posts/{post_id}/like"), postHandler::updateLikes)
                .andRoute(GET("/board/posts/{post_id}/like"), postHandler::selectUsersLikePost)
                .andRoute(GET("/board/posts/adjacent/{category_id}/{post_id}"), postHandler::readAdjacent)
                .andRoute(PATCH("/board/posts/{post_id}"), postHandler::update)
                .andRoute(DELETE("/board/posts/{post_id}"), postHandler::delete)
//                .andRoute(GET("/board/posts/page"), postHandler.readSelectedPage)
                .andRoute(POST("/board/posts"), postHandler::create)
                .andRoute(GET("/board/posts"), postHandler::readAll);
    }
}
