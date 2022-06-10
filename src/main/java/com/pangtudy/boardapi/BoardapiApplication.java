package com.pangtudy.boardapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import reactivefeign.spring.config.EnableReactiveFeignClients;

@EnableReactiveFeignClients
@SpringBootApplication
@EnableEurekaClient
public class BoardapiApplication {

    public static void main(String[] args) {
        SpringApplication.run(BoardapiApplication.class, args);
    }

}
