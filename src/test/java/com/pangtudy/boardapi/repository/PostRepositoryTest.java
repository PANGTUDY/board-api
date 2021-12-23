package com.pangtudy.boardapi.repository;

import com.pangtudy.boardapi.dto.Post;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.r2dbc.DataR2dbcTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import reactor.test.StepVerifier;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith(SpringExtension.class)
@DataR2dbcTest
class PostRepositoryTest {

    @Autowired
    private PostRepository postRepository;

    @BeforeClass
    @Test
    public void 필요한_객체들_주입되었는지_확인() {
        assertNotNull(postRepository);
    }

    @Test
    public void testSave_게시글_생성() {
        Post newPost = Post.builder()
                .categoryId(2)
                .title("북스터디 개요(1)")
                .date(LocalDateTime.parse("2021-10-27T12:00:00"))
                .writer("작성자")
                .build();
        postRepository.save(newPost)
                .as(StepVerifier::create)
                .consumeNextWith(p -> assertEquals(newPost, p))
                .verifyComplete();
    }

    @Test
    public void testSave_게시글_수정() {
        Post post = Post.builder()
                .postId(1)
                .categoryId(2)
                .title("북 스터디 개요(1)")
                .date(LocalDateTime.parse("2021-10-27T12:00:00"))
                .writer("작성자")
                .build();
        postRepository.save(post)
                .as(StepVerifier::create)
                .consumeNextWith(p -> assertEquals(post, p))
                .verifyComplete();
    }

    @Test
    public void testFindAll_게시글_전체검색() {
        postRepository.findAll()
                .as(StepVerifier::create)
                .consumeNextWith(p -> assertEquals("제목1", p.getTitle()))
                .consumeNextWith(p -> assertEquals("제목2", p.getTitle()))
                .consumeNextWith(p -> assertEquals("제목3", p.getTitle()))
                .consumeNextWith(p -> assertEquals("제목4", p.getTitle()))
                .consumeNextWith(p -> assertEquals("제목5", p.getTitle()))
                .verifyComplete();
    }

    @Test
    public void testFindById_게시글_아이디로_검색() {
        postRepository.findById(2)
                .as(StepVerifier::create)
                .consumeNextWith(p -> Assert.assertEquals("제목2", p.getTitle()))
                .verifyComplete();
    }

    @Test
    public void testDeleteById_게시글_삭제() {
        postRepository.deleteById(1)
                .as(StepVerifier::create)
                .expectNextCount(0)
                .verifyComplete();
        //TODO : 삭제 성공 실패 구분. 없는 아이디 넣어도 동작함..
    }

    @AfterClass
    @Test
    public void 게시글_테스트_종료() {
        //do something...
    }
}