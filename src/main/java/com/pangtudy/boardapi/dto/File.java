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
//@Entity(name = "Files(TBL_ATTACH)")
public class File {
    //@Column(name = "file_id", nullable = false)
    private int fileId;

    //@Column(name = "file_name", nullable = false, length = 100)
    private String fileName;

    //@Column(name = "file_path", nullable = false, length = 300)
    private String filePath;

    //@Column(name = "file_type", nullable = false, length = 50)
    private String fileType;

    //@ManyToOne
    //@JoinColumn(name = "posts_post_id") //posts 테이블의 post_id를 외래키로
    private Post post;
}