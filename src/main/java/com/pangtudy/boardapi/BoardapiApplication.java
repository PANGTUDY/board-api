package com.pangtudy.boardapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableEurekaClient
public class BoardapiApplication {

    public static void main(String[] args) {
        SpringApplication.run(BoardapiApplication.class, args);
    }

}
