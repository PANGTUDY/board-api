package com.pangtudy.boardapi.router;

import com.pangtudy.boardapi.handler.PostHandler;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RequestPredicates.*;

@Slf4j
@Component
@RequiredArgsConstructor
public class BoardRouter {
    private final PostHandler postHandler;
    //private final CategoryHandler categoryHandler;
    //private final CommentHandler commentHandler;
    //private final TagHandler tagHandler;
    //private final FileHandler fileHandler;

    /*
    * 여러 개의 router function을 체이닝한다.
    * 순서에 따라 차례대로 검증되기 때문에 가장 구체적인 route가 앞에 순서에 오도록 한다.
    * */
    @Bean
    public RouterFunction<ServerResponse> route() {
        return RouterFunctions
                //Post
                .route(GET("/board/posts/{post_id}"), postHandler::read)
                .andRoute(PATCH("/board/posts/{post_id}"), postHandler::update)
                .andRoute(DELETE("/board/posts/{post_id}"), postHandler::delete)
//                .andRoute(GET("/board/posts/page"), postHandler.readSelectedPage)
//                .andRoute(GET("/board/search"), postHandler.readAndSearch)
                .andRoute(POST("/board/posts"), postHandler::create)
                .andRoute(GET("/board/posts"), postHandler::readAll)
/*
                //Category
                .andRoute(PATCH("/board/categories/{category_id}"), categoryHandler.update)
                .andRoute(PATCH("/board/categories/{category_id}"), categoryHandler.delete)
                .andRoute(POST("/board/categories"), categoryHandler.create)
                .andRoute(GET("/board/categories"), categoryHandler.read)

                //Comment
                .andRoute(GET("/board/posts/{post_id}/comments/{comments_id}"), commentHandler.read)
                .andRoute(PATCH("/board/posts/{post_id}/comments/{comments_id}"), commentHandler.update)
                .andRoute(DELETE("/board/posts/{post_id}/comments/{comments_id}"), commentHandler.delete)
                .andRoute(POST("/board/posts/{post_id}/comments"), commentHandler.create)
                .andRoute(GET("/board/posts/{post_id}/comments"), commentHandler.readAll)

                //Tag
                .andRoute(PATCH("/board/posts/{post_id}/tags/{tags_id}"), tagHandler.update)
                .andRoute(DELETE("/board/posts/{post_id}/tags/{tags_id}"), tagHandler.delete)
                .andRoute(POST("/board/posts/{post_id}/tags"), tagHandler.create)
                .andRoute(GET("/board/posts/{post_id}/tags"), tagHandler.read)

                //File
                .andRoute(GET("/board/posts/{post_id}/files/{file_id}"), fileHandler.read)
                .andRoute(PATCH("/board/posts/{post_id}/files/{file_id}"), fileHandler.update)
                .andRoute(DELETE("/board/posts/{post_id}/files/{file_id}"), fileHandler.delete)
                .andRoute(POST("/board/posts/{post_id}/files"), fileHandler.create)
                .andRoute(GET("/board/posts/{post_id}/files"), fileHandler.readAll)
*/
        ;
    }
}
