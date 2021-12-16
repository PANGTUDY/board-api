package com.pangtudy.boardapi.repository;

import com.pangtudy.boardapi.dto.Category;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.r2dbc.DataR2dbcTest;
import org.springframework.r2dbc.core.DatabaseClient;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@ExtendWith(SpringExtension.class)
@DataR2dbcTest
public class CategoryRepositoryTest {
    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    DatabaseClient client;

    @BeforeClass
    @Test
    public void 필요한_객체들_주입되었는지_확인() {
        assertNotNull(categoryRepository);
        assertNotNull(client);
        //data.sql로 테이블에 데이터들을 미리 insert 해둠
    }

    @Test
    public void 카테고리_등록() {
        Category category = Category.builder()
                .categoryName("진하가만듦").build();
        Mono<Category> savedCategory = categoryRepository.save(category);
        Assertions.assertEquals(category.getCategoryName(), savedCategory.block().getCategoryName());
    }

    @Test
    public void 카테고리_전체검색() {
        Flux<Category> savedCategory = categoryRepository.findAll();
        // TODO
        // Assertions.assertEquals(category.getCategoryName(), savedCategory.block().getCategoryName());
    }

    @Test
    public void 카테고리_수정() {
        Category category = Category.builder()
                .categoryId(1)
                .categoryName("Spring").build();
        Mono<Category> savedCategory = categoryRepository.save(category);
        Assertions.assertEquals(category.getCategoryName(), savedCategory.block().getCategoryName());
    }

    @Test
    public void 카테고리_삭제() {
        Mono<Void> deletedCategory = categoryRepository.deleteById(2);
        //TODO : 삭제 성공 실패 구분
        Assertions.assertEquals(Optional.empty(), deletedCategory.block());
    }

    @Test
    public void DataBaseClient이용하여_sql문을_작성하여_카테고리_생성하고_아이디로_검색() {
        client.sql("INSERT INTO CATEGORY (category_name) VALUES (:categoryName)")
                .filter((statement, executeFunction) -> statement.returnGeneratedValues("category_id").execute())
                .bind("categoryName", "DataBase")
                .fetch()
                //.rowsUpdated()
                .first()
                .block();
        categoryRepository.findById(3)
                .as(StepVerifier::create)
                .consumeNextWith(p -> assertEquals("DataBase", p.getCategoryName()))
                .verifyComplete();
    }

    @AfterClass
    @Test
    public void 카테고리_테스트_종료() {
        //do something...
    }
}