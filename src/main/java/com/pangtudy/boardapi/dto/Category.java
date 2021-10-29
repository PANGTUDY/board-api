package com.pangtudy.boardapi.dto;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import java.util.ArrayList;
import java.util.List;

@Table
@ToString
@Data
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
//@Entity(name = "Category")
public class Category {
    @Id
    //@Column(name = "category_id")
    //@GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer categoryId;

    //@Column(name = "category_name", nullable = false, length = 100)
    private String categoryName;

    //@Column(name = "follow_id", nullable = true)
    private String followId;

    //@OneToMany(mappedBy = "category") //Post 엔티티에서 Category를 참조할 때 작성한 필드명
    private List<Post> posts = new ArrayList<>();
}