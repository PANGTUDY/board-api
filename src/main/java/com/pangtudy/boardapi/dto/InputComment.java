package com.pangtudy.boardapi.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDateTime;

@Table
@ToString
@Data
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class InputComment {
    private String writer;
    private String contents;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime date;
}
