package com.pangtudy.boardapi.dto;

import lombok.*;
import org.springframework.data.relational.core.mapping.Table;

@Table
@ToString
@Data
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
//@Entity(name = "CONTENTS")
public class Content {
    //@Column(name = "contents", length = 10000)
    private String contents;

    //@OneToOne
    //@JoinColumn(name = "posts_post_id") //posts 테이블의 post_id를 외래키로
    private Post post;
}