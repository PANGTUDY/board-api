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
//@Entity(name = "Users")
public class User {
    @Id
    //@GeneratedValue(strategy = GenerationType.AUTO)
    //@Column(name = "pwd")
    private String pwd;

    //@Column(name = "name", nullable = false, length = 50)
    private String name;

    //@Column(name = "nickname", nullable = false, unique = true, length = 50)
    private String nickname;

    //@Column(name = "message", nullable = false, length = 50)
    private String message;

    //@Column(name = "profile_img", length = 200)
    private String profileImg;
}