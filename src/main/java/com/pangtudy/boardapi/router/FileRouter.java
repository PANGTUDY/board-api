package com.pangtudy.boardapi.router;

import com.pangtudy.boardapi.entity.File;
import com.pangtudy.boardapi.handler.FileHandler;
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
public class FileRouter {
    private final FileHandler fileHandler;

    @RouterOperations({
            @RouterOperation(path = "/board/posts/{post_id}/files/{file_id}", produces = {
                    MediaType.APPLICATION_JSON_VALUE},
                    beanClass = FileHandler.class, method = RequestMethod.GET, beanMethod = "read",
                    operation = @Operation(operationId = "getFile", responses = {
                            @ApiResponse(responseCode = "200", description = "successful operation",
                                    content = @Content(schema = @Schema(implementation = File.class))),
                            @ApiResponse(responseCode = "400", description = "Invalid File details supplied")},
                            parameters = {@Parameter(in = ParameterIn.PATH, name = "post_id"),
                                    @Parameter(in = ParameterIn.PATH, name = "file_id")}
                    )),
            @RouterOperation(path = "/board/posts/{post_id}/files/{file_id}"
                    , produces = {
                    MediaType.APPLICATION_JSON_VALUE}, method = RequestMethod.PATCH, beanClass = FileHandler.class, beanMethod = "update",
                    operation = @Operation(operationId = "updateFile", responses = {
                            @ApiResponse(responseCode = "200", description = "successful operation", content = @Content(schema = @Schema(implementation = File.class))),
                            @ApiResponse(responseCode = "400", description = "Invalid File ID supplied"),
                            @ApiResponse(responseCode = "404", description = "File not found")},
                            parameters = {@Parameter(in = ParameterIn.PATH, name = "post_id"),
                                    @Parameter(in = ParameterIn.PATH, name = "file_id")}
                            , requestBody = @RequestBody(content = @Content(schema = @Schema(implementation = File.class))))
            ),
            @RouterOperation(path = "/board/posts/{post_id}/files/{file_id}", produces = {
                    MediaType.APPLICATION_JSON_VALUE}, method = RequestMethod.DELETE, beanClass = FileHandler.class, beanMethod = "delete"
                    , operation = @Operation(operationId = "deleteFile", responses = {
                    @ApiResponse(responseCode = "200", description = "successful operation", content = @Content(mediaType = "Boolean")),
                    @ApiResponse(responseCode = "400", description = "Invalid File ID supplied"),
                    @ApiResponse(responseCode = "404", description = "File not found")},
                    parameters = {@Parameter(in = ParameterIn.PATH, name = "post_id"),
                    @Parameter(in = ParameterIn.PATH, name = "file_id")}
            )),
            @RouterOperation(path = "/board/posts/{post_id}/files", produces = {
                    MediaType.APPLICATION_JSON_VALUE}, method = RequestMethod.POST, beanClass = FileHandler.class, beanMethod = "create",
                    operation = @Operation(operationId = "insertFile", responses = {
                            @ApiResponse(responseCode = "200", description = "successful operation", content = @Content(schema = @Schema(implementation = File.class))),
                            @ApiResponse(responseCode = "400", description = "Invalid File details supplied")},
                            parameters = {@Parameter(in = ParameterIn.PATH, name = "post_id")},
                            requestBody = @RequestBody(content = @Content(schema = @Schema(implementation = File.class)))
                    )),
/*            @RouterOperation(path = "/board/posts/{post_id}/files", produces = {
                    MediaType.APPLICATION_JSON_VALUE},
                    beanClass = FileHandler.class, method = RequestMethod.GET, beanMethod = "readAll",
                    operation = @Operation(operationId = "getFiles", responses = {
                            @ApiResponse(responseCode = "200", description = "successful operation",
                                    content = @Content(schema = @Schema(implementation = File.class)))},
                            parameters = {@Parameter(in = ParameterIn.PATH, name = "post_id")}
                    ))*/
    })
    @Bean
    public RouterFunction<ServerResponse> fileRoutes() {
        return RouterFunctions
                .route(GET("/board/posts/{post_id}/files/{file_id}"), fileHandler::read)
                .andRoute(PATCH("/board/posts/{post_id}/files/{file_id}"), fileHandler::update)
                .andRoute(DELETE("/board/posts/{post_id}/files/{file_id}"), fileHandler::delete)
                .andRoute(POST("/board/posts/{post_id}/files"), fileHandler::create);
//                .andRoute(GET("/board/posts/{post_id}/files"), fileHandler::readAll);
    }
}
