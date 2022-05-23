package com.pangtudy.boardapi.dto;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
@Builder
public class User {
    private Integer id;
    private String email;
    private String name;
}
