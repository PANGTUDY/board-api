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
//@Entity(name = "TAG_TABLE")
public class Tag {
    @Id
    //@GeneratedValue(strategy = GenerationType.IDENTITY)
    private String tag;

    //@Column(name = "post_ids", length = 10000)
    private String postIds;
}