package com.pangtudy.boardapi.handler;

import com.pangtudy.boardapi.dto.Category;
import com.pangtudy.boardapi.dto.InputCategory;
import com.pangtudy.boardapi.repository.CategoryRepository;
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
public class CategoryHandler {
    private final CategoryRepository categoryRepository;

    public Mono<ServerResponse> create(ServerRequest req) {
        Mono<InputCategory> newCategory = req.bodyToMono(InputCategory.class);
        Mono<Category> savedCategory = newCategory
                .flatMap(value -> Mono.just(new Category(null, value.getCategoryName())))
                .flatMap(category -> categoryRepository.save(category));

        return ok().contentType(APPLICATION_JSON).body(BodyInserters.fromProducer(savedCategory, Category.class));
    }

    public Mono<ServerResponse> readAll(ServerRequest req) {
        Flux<Category> categories = categoryRepository.findAll();
        return ok().contentType(APPLICATION_JSON).body(BodyInserters.fromProducer(categories, Category.class));
    }

    public Mono<ServerResponse> read(ServerRequest req) {
        int categoryId = Integer.valueOf(req.pathVariable("category_id"));
        return categoryRepository.findById(categoryId)
                .flatMap(category -> ok().contentType(APPLICATION_JSON).bodyValue(category))
                .switchIfEmpty(ServerResponse.notFound().build());
    }

    public Mono<ServerResponse> update(ServerRequest req) {
        int categoryId = Integer.valueOf(req.pathVariable("category_id"));
        Mono<Category> newCategory = req.bodyToMono(Category.class).map(category -> {
            category.setCategoryId(categoryId);
            return category;
        });
        Mono<Category> savedCategory = newCategory.flatMap(category -> categoryRepository.save(category));
        return ok().contentType(APPLICATION_JSON).body(BodyInserters.fromProducer(savedCategory, Category.class));
    }

    public Mono<ServerResponse> delete(ServerRequest req) {
        int categoryId = Integer.valueOf(req.pathVariable("category_id"));
        return ok().build(categoryRepository.deleteById(categoryId));
    }
}
