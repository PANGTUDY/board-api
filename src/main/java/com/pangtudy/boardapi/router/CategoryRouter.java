package com.pangtudy.boardapi.router;

import com.pangtudy.boardapi.dto.Category;
import com.pangtudy.boardapi.dto.InputCategory;
import com.pangtudy.boardapi.handler.CategoryHandler;
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
public class CategoryRouter {
    private final CategoryHandler categoryHandler;

    @Bean
    @RouterOperations({
            @RouterOperation(path = "/board/categories/{category_id}", produces = {
                    MediaType.APPLICATION_JSON_VALUE},
                    beanClass = CategoryHandler.class,
                    method = RequestMethod.GET, beanMethod = "read",
                    operation = @Operation(summary = "특정 카테고리 조회", operationId = "getCategory",
                            responses = {
                                    @ApiResponse(responseCode = "200", description = "successful operation",
                                            content = @Content(schema = @Schema(implementation = Category.class))),
                                    @ApiResponse(responseCode = "400", description = "Invalid Category details supplied")},
                            parameters = {@Parameter(in = ParameterIn.PATH, name = "category_id")}
                    )),
            @RouterOperation(path = "/board/categories/{category_id}", produces = {
                    MediaType.APPLICATION_JSON_VALUE},
                    method = RequestMethod.PATCH, beanClass = CategoryHandler.class, beanMethod = "update",
                    operation = @Operation(summary = "카테고리 수정", operationId = "updateCategory",
                            responses = {
                                    @ApiResponse(responseCode = "200", description = "successful operation",
                                            content = @Content(schema = @Schema(implementation = Category.class))),
                                    @ApiResponse(responseCode = "400", description = "Invalid Category ID supplied"),
                                    @ApiResponse(responseCode = "404", description = "Category not found")},
                            parameters = {
                                    @Parameter(in = ParameterIn.PATH, name = "category_id")},
                            requestBody = @RequestBody(content = @Content(schema = @Schema(implementation = InputCategory.class))))
            ),
            @RouterOperation(path = "/board/categories/{category_id}", produces = {
                    MediaType.APPLICATION_JSON_VALUE},
                    method = RequestMethod.DELETE, beanClass = CategoryHandler.class, beanMethod = "delete",
                    operation = @Operation(summary = "카테고리 삭제", operationId = "deleteCategory",
                            responses = {
                                    @ApiResponse(responseCode = "200", description = "successful operation",
                                            content = @Content(mediaType = "Boolean")),
                                    @ApiResponse(responseCode = "400", description = "Invalid Category ID supplied"),
                                    @ApiResponse(responseCode = "404", description = "Category not found")},
                            parameters = {
                                    @Parameter(in = ParameterIn.PATH, name = "category_id")}
                    )),
            @RouterOperation(path = "/board/categories", produces = {
                    MediaType.APPLICATION_JSON_VALUE},
                    method = RequestMethod.POST, beanClass = CategoryHandler.class, beanMethod = "create",
                    operation = @Operation(summary = "카테고리 생성", operationId = "insertCategory",
                            responses = {
                                    @ApiResponse(responseCode = "200", description = "successful operation",
                                            content = @Content(schema = @Schema(implementation = Category.class))),
                                    @ApiResponse(responseCode = "400", description = "Invalid Category details supplied")},
                            requestBody = @RequestBody(content = @Content(schema = @Schema(implementation = InputCategory.class)))
                    )),
            @RouterOperation(path = "/board/categories", produces = {
                    MediaType.APPLICATION_JSON_VALUE}, beanClass = CategoryHandler.class,
                    method = RequestMethod.GET, beanMethod = "readAll",
                    operation = @Operation(summary = "카테고리 조회", operationId = "getCategories",
                            responses = {
                                    @ApiResponse(responseCode = "200", description = "successful operation",
                                            content = @Content(schema = @Schema(implementation = Category.class)))}
                    ))
    })
    public RouterFunction<ServerResponse> categoryRoutes() {
        return RouterFunctions
                .route(POST("/board/categories"), categoryHandler::create)
                .andRoute(GET("/board/categories"), categoryHandler::readAll)
                .andRoute(GET("/board/categories/{category_id}"), categoryHandler::read)
                .andRoute(PATCH("/board/categories/{category_id}"), categoryHandler::update)
                .andRoute(DELETE("/board/categories/{category_id}"), categoryHandler::delete)
                .andRoute(POST("/board/categories"), categoryHandler::create);
    }
}
