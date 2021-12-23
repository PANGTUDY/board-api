package com.pangtudy.boardapi.repository;

import com.pangtudy.boardapi.dto.File;
import org.junit.AfterClass;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.r2dbc.DataR2dbcTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import reactor.test.StepVerifier;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(SpringExtension.class)
@DataR2dbcTest
class FileRepositoryTest {
    @Autowired
    private FileRepository fileRepository;

    @Test
    public void testSave_파일_생성() {
        File file = File.builder()
                .fileName("파일3이름")
                .filePath("파일3경로")
                .fileType("파일3타입")
                .build();
        fileRepository.save(file)
                .as(StepVerifier::create)
                .consumeNextWith(f -> assertEquals(file, f))
                .verifyComplete();
    }

    @Test
    public void testSave_파일_수정() {
        File file = File.builder()
                .fileId(1)
                .fileName("수정된파일1이름")
                .filePath("수정된파일1경로")
                .fileType("수정된파일1타입")
                .build();
        fileRepository.save(file)
                .as(StepVerifier::create)
                .consumeNextWith(f -> assertEquals(file, f))
                .verifyComplete();
    }

    @Test
    public void testFindAll_파일_전체검색() {
        fileRepository.findAll()
                .as(StepVerifier::create)
                .consumeNextWith(f -> assertEquals("파일1이름", f.getFileName()))
                .consumeNextWith(f -> assertEquals("파일2이름", f.getFileName()))
                .verifyComplete();
    }

    @Test
    public void testFindById_파일_아이디로_검색() {
        fileRepository.findById(1)
                .as(StepVerifier::create)
                .consumeNextWith(f -> assertEquals("파일1이름", f.getFileName()))
                .verifyComplete();
    }

    @Test
    public void testDeleteById_파일_삭제() {
        fileRepository.deleteById(1)
                .as(StepVerifier::create)
                .expectNextCount(0)
                .verifyComplete();
        //TODO : 삭제 성공 실패 구분. 없는 아이디 넣어도 동작함..
    }

    @AfterClass
    @Test
    public void 파일_테스트_종료() {
        //do something...
    }

}