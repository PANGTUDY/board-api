package com.pangtudy.boardapi.handler;

import com.pangtudy.boardapi.entity.Tag;
import com.pangtudy.boardapi.repository.TagRepository;
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
//@Tag(name = "tag", description = "태그 API")
public class TagHandler {
    private final TagRepository tagRepository;

    public Mono<ServerResponse> create(ServerRequest req) {
        Mono<Tag> savedTag = req.bodyToMono(Tag.class).flatMap(post -> tagRepository.save(post));
        return ok().contentType(APPLICATION_JSON).body(BodyInserters.fromProducer(savedTag, Tag.class));
    }

    //게시글의 tag목록
    public Mono<ServerResponse> read(ServerRequest req) {
        int postId = Integer.valueOf(req.pathVariable("post_id"));
        return tagRepository.findById(postId)
                .flatMap(Tag -> ok().contentType(APPLICATION_JSON).bodyValue(Tag))
                .switchIfEmpty(ServerResponse.notFound().build());
    }

    public Mono<ServerResponse> update(ServerRequest req) {
        int postId = Integer.valueOf(req.pathVariable("post_id"));
        int tagId = Integer.valueOf(req.pathVariable("tag_id"));

        //Tag하나 string에 추가해서 udpate
        Mono<Tag> newTag = req.bodyToMono(Tag.class).map(tag -> {
            tag.setTag(tag.getTag() + tagId);
            return tag;
        });

        Mono<Tag> savedTag = newTag.flatMap(Tag -> tagRepository.save(Tag));
        return ok().contentType(APPLICATION_JSON).body(BodyInserters.fromProducer(savedTag, Tag.class));
    }

    public Mono<ServerResponse> delete(ServerRequest req) {
        int postId = Integer.valueOf(req.pathVariable("post_id"));
        int tagId = Integer.valueOf(req.pathVariable("tag_id"));
        //TODO : postId의 tags string에서 tagId에 해당하는 것만 지우기
        return ok().build(tagRepository.deleteById(tagId));
    }
}
