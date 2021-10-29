package com.pangtudy.boardapi.dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import lombok.*;
import org.springframework.data.annotation.Id;
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
//@Entity(name = "Posts")
public class Post {
    @Id
    //@GeneratedValue(strategy = GenerationType.AUTO)
    private int postId;

    private int categoryId;

    //@Column(name = "tags", nullable = false, length = 100)
    private String tags;

    //@Column(name = "follow_id", nullable = false)
    private int followId;

    //@Column(name = "title", nullable = false, length = 50)
    private String title;

    //@Column(name = "date", nullable = false)
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    private LocalDateTime date;

    //@Column(name = "writer", nullable = false, length = 50)
    private String writer;

    //@Column(name = "likes")
    private int likes;

    //@OneToMany(mappedBy = "post") //File 엔티티에서 Post를 참조할 때 작성한 필드명
    //private List<FileDTO> attached = new ArrayList<>();
    private String attached;

    //@ManyToOne
    //@JoinColumn(name = "category_category_id") //category 테이블의 category_id를 외래키로
    //private CategoryDTO category;

    //@OneToOne
    //@JoinColumn(name = "users_user_email") //users 테이블의 users_email을 외래키로
    //private UserDTO user;
}