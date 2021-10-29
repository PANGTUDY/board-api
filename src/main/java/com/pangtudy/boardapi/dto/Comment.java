package com.pangtudy.boardapi.dto;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import java.util.Date;

@Table
@ToString
@Data
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
//@Entity(name = "Comments")
public class Comment {
    @Id
    //@Column(name = "comment_id", nullable = false)
    private int commentId;

    //@Column(name = "follow_id", nullable = false)
    private int followId;

    //@Column(name = "writer", nullable = false, length = 50)
    private String writer;

    //@Column(name = "contents", nullable = false, length = 500)
    private String contents;

    //@Column(name = "date", nullable = false)
    private Date date;

    //@Column(name = "modified_date", nullable = false)
    private Date modifiedDate;

    //@OneToOne
    //@JoinColumn(name = "posts_post_id") //posts 테이블의 post_id를 외래키로
    private Post post;
}