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
public class InputCategory {
    private String categoryName;
}