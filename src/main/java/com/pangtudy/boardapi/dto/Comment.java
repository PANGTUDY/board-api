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
public class Comment {
    @Id
    private int commentId;
    private int followId;
    private String writer;
    private String contents;
    private Date date;
    private Date modifiedDate;
    private int postId;
}