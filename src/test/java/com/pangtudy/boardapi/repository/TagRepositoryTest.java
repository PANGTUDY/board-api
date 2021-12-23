package com.pangtudy.boardapi.repository;

import com.pangtudy.boardapi.dto.Tag;
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
class TagRepositoryTest {
    @Autowired
    private TagRepository tagRepository;

    @Test
    public void testSave_태그_생성() {
        Tag newTag = Tag.builder()
                .postId(2)
                .tag("새로운,태그를,만들어요")
                .build();
        tagRepository.save(newTag)
                .as(StepVerifier::create)
                .consumeNextWith(t -> assertEquals(newTag, t))
                .verifyComplete();
    }

    @Test
    public void testSave_태그_수정() {
        Tag newTag = Tag.builder()
                .postId(2)
                .tag("수정된,태그를,만들어요")
                .build();
        tagRepository.save(newTag)
                .as(StepVerifier::create)
                .consumeNextWith(t -> assertEquals(newTag, t))
                .verifyComplete();
    }

    @Test
    public void testFindAll_태그_전체검색() {
        tagRepository.findAll()
                .as(StepVerifier::create)
                .consumeNextWith(t -> assertEquals("태그,이렇게,들어갈예정", t.getTag()))
                .consumeNextWith(t -> assertEquals("아파치,자바,리눅스", t.getTag()))
                .consumeNextWith(t -> assertEquals("ava,Stream,CompletableFuture", t.getTag()))
                .consumeNextWith(t -> assertEquals("Ubuntu,Spring", t.getTag()))
                .consumeNextWith(t -> assertEquals("http,debug", t.getTag()))
                .verifyComplete();
    }

    @Test
    public void testFindById_게시글_아이디로_검색() {
        tagRepository.findById(1)
                .as(StepVerifier::create)
                .consumeNextWith(t -> Assert.assertEquals("태그,이렇게,들어갈예정", t.getTag()))
                .verifyComplete();
    }

    @Test
    public void testDeleteById_태그_삭제() {
        //TODO : 태그 하나씩 삭제한다는 것은 곧 수정을 뜻함.. 게시글에 태그 붙이는 방식을 바꿔야할듯.
    }

    @AfterClass
    @Test
    public void 태그_테스트_종료() {
        //do something...
    }

}