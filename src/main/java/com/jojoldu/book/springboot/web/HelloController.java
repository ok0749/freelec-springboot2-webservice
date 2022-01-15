package com.jojoldu.book.springboot.web;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

// 컨트롤러를 JSON을 반환하는 컨트롤러로 만든다.
@RestController
public class HelloController {

    // Get 요청을 받는 API를 만든다.
    @GetMapping("/hello")
    public String hello() {
        return "hello";
    }
}
