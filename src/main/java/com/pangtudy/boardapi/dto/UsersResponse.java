package com.pangtudy.boardapi.dto;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Data
@Getter
@Setter
@Builder
public class UsersResponse {
    private String status;
    private String message;
    private List<User> data;
}
