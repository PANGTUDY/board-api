package com.pangtudy.boardapi.dto;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Table
@ToString
@Data
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class File {
    @Id
    private int fileId;
    private String fileName;
    private String filePath;
    private String fileType;
    //private int postId;
}