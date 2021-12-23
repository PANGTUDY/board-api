package com.pangtudy.boardapi.repository;

import com.pangtudy.boardapi.dto.Comment;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.r2dbc.DataR2dbcTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import reactor.test.StepVerifier;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(SpringExtension.class)
@DataR2dbcTest
class CommentRepositoryTest {
    @Autowired
    private CommentRepository commentRepository;

    @Test
    public void testSave_댓글_생성() {
        Comment comment = Comment.builder()
                .postId(2)
                .contents("댓글 내용입니다.")
                .writer("작성자")
                .date(LocalDateTime.parse("2021-10-27T12:00:00"))
                .build();
        commentRepository.save(comment)
                .as(StepVerifier::create)
                .consumeNextWith(c -> assertEquals(comment, c))
                .verifyComplete();
    }

    @Test
    public void testSave_댓글_수정() {
        Comment comment = Comment.builder()
                .commentId(1)
                .postId(2)
                .contents("수정된 댓글 내용입니다.")
                .writer("작성자")
                .date(LocalDateTime.parse("2021-10-27T14:00:00"))
                .build();
        commentRepository.save(comment)
                .as(StepVerifier::create)
                .consumeNextWith(c -> assertEquals(comment, c))
                .verifyComplete();
    }

    @Test
    public void testFindAll_댓글_전체_검색() {
        commentRepository.findAll()
                .as(StepVerifier::create)
                .consumeNextWith(c -> assertEquals("댓글내용입니닷.", c.getContents()))
                .consumeNextWith(c -> assertEquals("대댓내용입니다.", c.getContents()))
                .verifyComplete();
    }

    @Test
    public void testFindByPostId_댓글_게시글_내_검색() {
        //TODO : commentRepository.findByPostId 테스트
    }

    @Test
    public void testFindById_댓글_아이디로_검색() {
        commentRepository.findById(1)
                .as(StepVerifier::create)
                .consumeNextWith(c -> Assert.assertEquals("댓글내용입니닷", c.getContents()))
                .verifyComplete();
    }

    @Test
    public void testDeleteById_댓글_삭제() {
        commentRepository.deleteById(1)
                .as(StepVerifier::create)
                .expectNextCount(0)
                .verifyComplete();
        //TODO : 삭제 성공 실패 구분. 없는 아이디 넣어도 동작함..
    }

    @AfterClass
    @Test
    public void 댓글_테스트_종료() {
        //do something...
    }

}