package com.pangtudy.boardapi.dto;

import lombok.*;

@ToString
@Data
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class InputUser {
    private Integer userId;
    private String userName;
}
