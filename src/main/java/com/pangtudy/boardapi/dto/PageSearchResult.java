package com.pangtudy.boardapi.dto;

import com.pangtudy.boardapi.entity.Post;
import lombok.*;

import java.io.Serializable;
import java.util.List;

@ToString
@Data
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PageSearchResult implements Serializable {
    private List<Post> posts;
    private long currPageNum;
    private long totalPageNum;
}
