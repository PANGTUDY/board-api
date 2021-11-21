package com.pangtudy.boardapi.handler;

import com.pangtudy.boardapi.dto.File;
import com.pangtudy.boardapi.repository.FileRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.web.reactive.function.server.ServerResponse.ok;

@Slf4j
@Component
@RequiredArgsConstructor
public class FileHandler {
    private final FileRepository fileRepository;

    public Mono<ServerResponse> create(ServerRequest req) {
        Mono<File> savedFile = req.bodyToMono(File.class).flatMap(file -> fileRepository.save(file));
        return ok().contentType(APPLICATION_JSON).body(BodyInserters.fromProducer(savedFile, File.class));
    }

/*    public Mono<ServerResponse> readAll(ServerRequest req) {
        int postId = Integer.valueOf(req.pathVariable("post_id"));
        return fileRepository.findById(postId)
                .flatMap(post -> ok().contentType(APPLICATION_JSON).bodyValue(post))
                .switchIfEmpty(ServerResponse.notFound().build());
    }*/

    public Mono<ServerResponse> read(ServerRequest req) {
        int postId = Integer.valueOf(req.pathVariable("post_id"));
        int fileId = Integer.valueOf(req.pathVariable("file_id"));
        return fileRepository.findById(fileId)
                .flatMap(file -> ok().contentType(APPLICATION_JSON).bodyValue(file))
                .switchIfEmpty(ServerResponse.notFound().build());
    }

    public Mono<ServerResponse> update(ServerRequest req) {
        int postId = Integer.valueOf(req.pathVariable("post_id"));
        int fileId = Integer.valueOf(req.pathVariable("file_id"));
        Mono<File> newFile = req.bodyToMono(File.class).map(file -> {
            file.setFileId(fileId);
            return file;
        });
        Mono<File> savedFile = newFile.flatMap(file -> fileRepository.save(file));
        return ok().contentType(APPLICATION_JSON).body(BodyInserters.fromProducer(savedFile, File.class));
    }

    public Mono<ServerResponse> delete(ServerRequest req) {
        int postId = Integer.valueOf(req.pathVariable("post_id"));
        int fileId = Integer.valueOf(req.pathVariable("file_id"));
        return ok().build(fileRepository.deleteById(fileId));
    }
}
