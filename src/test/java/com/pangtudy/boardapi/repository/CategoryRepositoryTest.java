package com.pangtudy.boardapi.repository;

import com.pangtudy.boardapi.dto.Category;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.r2dbc.DataR2dbcTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import reactor.test.StepVerifier;

import static org.junit.Assert.assertEquals;

@ExtendWith(SpringExtension.class)
@DataR2dbcTest
public class CategoryRepositoryTest {

    @Autowired
    private CategoryRepository categoryRepository;

    @BeforeClass
    @Test
    public void 필요한_객체들_주입되었는지_확인() {
        Assertions.assertNotNull(categoryRepository);
    }

    @Test
    public void testSave_카테고리_생성() {
        Category newCategory = Category.builder().categoryName("Spring").build();
        categoryRepository.save(newCategory)
                .as(StepVerifier::create)
                .consumeNextWith(p -> assertEquals("Spring", p.getCategoryName()))
                .verifyComplete();
    }

    @Test
    public void testSave_카테고리_수정() {
        Category category = Category.builder()
                .categoryId(1)
                .categoryName("Spring").build();
        categoryRepository.save(category)
                .as(StepVerifier::create)
                .consumeNextWith(c -> assertEquals(category, c))
                .verifyComplete();
    }

    @Test
    public void testFindAll_카테고리_전체검색() {
        categoryRepository.findAll()
                .as(StepVerifier::create)
                .consumeNextWith(p -> assertEquals("Java", p.getCategoryName()))
                .consumeNextWith(p -> assertEquals("BookStudy", p.getCategoryName()))
                .verifyComplete();
    }

    @Test
    public void testFindById_카테고리_아이디로_검색() {
        categoryRepository.findById(2)
                .as(StepVerifier::create)
                .consumeNextWith(c -> assertEquals("BookStudy", c.getCategoryName()))
                .verifyComplete();
    }

    @Test
    public void testDeleteById_카테고리_삭제() {
        categoryRepository.deleteById(2)
                .as(StepVerifier::create)
                .expectNextCount(0)
                .verifyComplete();
        //TODO : 삭제 성공 실패 구분. 없는 아이디 넣어도 동작함..
    }

    @AfterClass
    @Test
    public void 카테고리_테스트_종료() {
        //do something...
    }
}