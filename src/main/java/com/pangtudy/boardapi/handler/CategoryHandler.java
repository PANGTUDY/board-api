package com.pangtudy.boardapi.handler;

import com.pangtudy.boardapi.dto.InputCategory;
import com.pangtudy.boardapi.dto.User;
import com.pangtudy.boardapi.entity.Category;
import com.pangtudy.boardapi.repository.CategoryRepository;
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
@Tag(name = "category", description = "카테고리 API")
public class CategoryHandler {
    private final CategoryRepository categoryRepository;
    private final UserRepository userRepository;

    public Mono<ServerResponse> getUsers(ServerRequest req){
        Flux<User> userFlux = userRepository.getAllUser();
        return ok().contentType(APPLICATION_JSON).body(BodyInserters.fromProducer(userFlux, User.class));
    }

    public Mono<ServerResponse> getUser(ServerRequest req){
        int userId = Integer.parseInt(req.pathVariable("id"));
        Mono<User> userMono = userRepository.getUser(userId);
        return ok().contentType(APPLICATION_JSON).body(BodyInserters.fromProducer(userMono, User.class));
    }

    public Mono<ServerResponse> create(ServerRequest req) {
        Mono<InputCategory> newCategory = req.bodyToMono(InputCategory.class);
        Mono<Category> savedCategory = newCategory
                .flatMap(value -> Mono.just(Category.builder().categoryName(value.getCategoryName()).build()))
                .flatMap(category -> categoryRepository.save(category));

        return ok().contentType(APPLICATION_JSON).body(BodyInserters.fromProducer(savedCategory, Category.class));
    }

    public Mono<ServerResponse> readAll(ServerRequest req) {
        Flux<Category> categories = categoryRepository.findAll();
        return ok().contentType(APPLICATION_JSON).body(BodyInserters.fromProducer(categories, Category.class));
    }

    public Mono<ServerResponse> read(ServerRequest req) {
        int categoryId = Integer.valueOf(req.pathVariable("category_id"));
        Mono<Category> category = categoryRepository.findByIdWithPosts(categoryId);
        return ok().contentType(APPLICATION_JSON).body(BodyInserters.fromProducer(category, Category.class));
    }

    public Mono<ServerResponse> update(ServerRequest req) {
        int categoryId = Integer.valueOf(req.pathVariable("category_id"));
        Mono<Category> oldCategory = categoryRepository.findById(categoryId);
        Mono<InputCategory> newCategory = req.bodyToMono(InputCategory.class);
        Mono<Category> savedCategory = newCategory
                .flatMap(value -> Mono.just(Category.builder()
                        .categoryId(categoryId)
                        .categoryName(value.getCategoryName())
                        .build()))
                .flatMap(category -> categoryRepository.save(category));

        return ok().contentType(APPLICATION_JSON).body(BodyInserters.fromProducer(savedCategory, Category.class));
    }

    public Mono<ServerResponse> delete(ServerRequest req) {
        int categoryId = Integer.valueOf(req.pathVariable("category_id"));
        return ok().build(categoryRepository.deleteById(categoryId));
    }
}
