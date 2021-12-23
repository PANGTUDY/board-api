package com.pangtudy.boardapi.repository;

import com.pangtudy.boardapi.dto.Content;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.r2dbc.DataR2dbcTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import reactor.test.StepVerifier;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(SpringExtension.class)
@DataR2dbcTest
class ContentRepositoryTest {
    @Autowired
    private ContentRepository contentRepository;

    @Test
    public void testSave_컨텐츠_생성() {
        Content newContent = Content.builder()
                .postId(1)
                .contents("원본 내용이다.")
                .build();
        contentRepository.save(newContent)
                .as(StepVerifier::create)
                .consumeNextWith(c -> assertEquals(newContent, c))
                .verifyComplete();
    }

    @Test
    public void testSave_컨텐츠_수정() {
        Content newContent = Content.builder()
                .postId(1)
                .contents("수정된 내용이다.")
                .build();
        contentRepository.save(newContent)
                .as(StepVerifier::create)
                .consumeNextWith(c -> assertEquals(newContent, c))
                .verifyComplete();
    }

    @Test
    public void testFindById_게시글_아이디로_검색() {
        contentRepository.findById(1)
                .as(StepVerifier::create)
                .consumeNextWith(p -> Assert.assertEquals("첫번째 포스트의 내용입니다.", p.getContents()))
                .verifyComplete();
    }

    @Test
    public void testDeleteById_컨텐츠_삭제() {
        contentRepository.deleteById(1)
                .as(StepVerifier::create)
                .expectNextCount(0)
                .verifyComplete();
        //TODO : 삭제 성공 실패 구분. 없는 아이디 넣어도 동작함..
        //내용이 없는 post가능?
    }

    @AfterClass
    @Test
    public void 컨텐츠_테스트_종료() {
        //do something...
    }

}