package com.pangtudy.boardapi.dto;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
@Builder
public class UserResponse {
    private String status;
    private String message;
    private User data;
}
