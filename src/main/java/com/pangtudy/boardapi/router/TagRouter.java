package com.pangtudy.boardapi.router;

import com.pangtudy.boardapi.dto.Tag;
import com.pangtudy.boardapi.handler.TagHandler;
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
public class TagRouter {
    private final TagHandler tagHandler;

    @RouterOperations({
            @RouterOperation(path = "/board/posts/{post_id}/tags/{tag_id}", produces = {
                    MediaType.APPLICATION_JSON_VALUE},
                    beanClass = TagHandler.class, method = RequestMethod.GET, beanMethod = "read",
                    operation = @Operation(operationId = "getTag", responses = {
                            @ApiResponse(responseCode = "200", description = "successful operation",
                                    content = @Content(schema = @Schema(implementation = Tag.class))),
                            @ApiResponse(responseCode = "400", description = "Invalid Tag details supplied")},
                            parameters = {@Parameter(in = ParameterIn.PATH, name = "post_id"),
                                    @Parameter(in = ParameterIn.PATH, name = "tag_id")}
                    )),
            @RouterOperation(path = "/board/posts/{post_id}/tags/{tag_id}"
                    , produces = {
                    MediaType.APPLICATION_JSON_VALUE}, method = RequestMethod.PATCH, beanClass = TagHandler.class, beanMethod = "update",
                    operation = @Operation(operationId = "updateTag", responses = {
                            @ApiResponse(responseCode = "200", description = "successful operation", content = @Content(schema = @Schema(implementation = Tag.class))),
                            @ApiResponse(responseCode = "400", description = "Invalid Tag ID supplied"),
                            @ApiResponse(responseCode = "404", description = "Tag not found")},
                            parameters = {@Parameter(in = ParameterIn.PATH, name = "post_id"),
                                    @Parameter(in = ParameterIn.PATH, name = "tag_id")}
                            , requestBody = @RequestBody(content = @Content(schema = @Schema(implementation = Tag.class))))
            ),
            @RouterOperation(path = "/board/posts/{post_id}/tags/{tag_id}", produces = {
                    MediaType.APPLICATION_JSON_VALUE}, method = RequestMethod.DELETE, beanClass = TagHandler.class, beanMethod = "delete"
                    , operation = @Operation(operationId = "deleteTag", responses = {
                    @ApiResponse(responseCode = "200", description = "successful operation", content = @Content(mediaType = "Boolean")),
                    @ApiResponse(responseCode = "400", description = "Invalid Tag ID supplied"),
                    @ApiResponse(responseCode = "404", description = "Tag not found")},
                    parameters = {@Parameter(in = ParameterIn.PATH, name = "post_id"),
                            @Parameter(in = ParameterIn.PATH, name = "tag_id")}
            )),
            @RouterOperation(path = "/board/posts/{post_id}/tags", produces = {
                    MediaType.APPLICATION_JSON_VALUE}, method = RequestMethod.POST, beanClass = TagHandler.class, beanMethod = "create",
                    operation = @Operation(operationId = "insertTag", responses = {
                            @ApiResponse(responseCode = "200", description = "successful operation", content = @Content(schema = @Schema(implementation = Tag.class))),
                            @ApiResponse(responseCode = "400", description = "Invalid Tag details supplied")},
                            parameters = {@Parameter(in = ParameterIn.PATH, name = "post_id")},
                            requestBody = @RequestBody(content = @Content(schema = @Schema(implementation = Tag.class)))
                    )),
    })
    @Bean
    public RouterFunction<ServerResponse> tagRoutes() {
        return RouterFunctions
                .route(PATCH("/board/posts/{post_id}/tags/{tag_id}"), tagHandler::update)
                .andRoute(DELETE("/board/posts/{post_id}/tags/{tag_id}"), tagHandler::delete)
                .andRoute(POST("/board/posts/{post_id}/tags"), tagHandler::create)
                .andRoute(GET("/board/posts/{post_id}/tags"), tagHandler::read);
    }
}
