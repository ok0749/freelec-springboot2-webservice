package com.jojoldu.book.springboot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

// @SpringBootApplication -> 스프링 부트의 자동 설정, 스피링 Bean 읽기와 생성을 모두 자동으로 설정
@SpringBootApplication
public class Application {
    public static void main(String[] args) {
        // 내장 WAS(WEB APPLICATION SERVER) 실행
        SpringApplication.run(Application.class, args);
    }
}
