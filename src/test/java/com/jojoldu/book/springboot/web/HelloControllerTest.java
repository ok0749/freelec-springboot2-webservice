package com.jojoldu.book.springboot.web;

import com.jojoldu.book.springboot.config.auth.SecurityConfig;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


// 테스트를 진행할 때 JUnit에 내장된 실행자 외에 다른 실행자를 실행시킨다.
// 여기서는 SpringExtension라는 스프링 실행자를 사용
// 스프링 부트 테스트와 JUnit 사이의 연결자 역할
@ExtendWith(SpringExtension.class)
// 여러 스프링 테스트 어노테이션 중 Web(Spring MVC)에 집중할 수 있는 어노테이션
// 선언할 경우 @Controller, @ControllerAdvice 등을 사용할 수 있다.
// @Service, @Component, @Repository 등은 사용할 수 없다.
// 여기서는 Controller만 사용하기 때문에 선언
@WebMvcTest(controllers = HelloController.class,
        // 스캔 대상에서 SecurityConfig를 제거
        excludeFilters = {
        @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = SecurityConfig.class)
        })
public class HelloControllerTest {
    // 스프링이 관리하는 빈(Bean)을 주입한다.
    @Autowired
    // 웹 API를 테스트할 때 사용
    // 스프링 MVC 테스트의 시작점
    private MockMvc mvc;

    @Test
    @WithMockUser(roles="USER")
    public void hello() throws Exception {
        String hello = "hello";
        // MockMvc를 통해 /hello 주소로 HTTP GET 요청
        // 체이닝이 지원됨
        mvc.perform(get("/hello"))
                // mvc.perform의 결과 검증
                // HTTP Header의 Status 검증
                // 여기서는 200인지 아닌지 검증
                .andExpect(status().isOk())
                // 응답 본문 내용 검증
                // Controller에서 "hello"를 리턴하기 때문에 이 값이 맞는지 검증
                .andExpect(content().string(hello));
    }

    @Test
    @WithMockUser(roles="USER")
    public void helloDto() throws Exception {
        String name = "hello";
        int amount = 1000;

        mvc.perform(get("/hello/dto")
                        // 요청 파라미터 설정
                        // 값은 String만 되므로 문자열로 변경
                        .param("name", name)
                        .param("amount", String.valueOf(amount)))
                .andExpect(status().isOk())
                // jsonPath: JSON 응답값을 필드별로 검증할 수 있는 메서드
                // $를 기준으로 필드명 명시
                .andExpect(jsonPath("$.name", is(name)))
                .andExpect(jsonPath("$.amount", is(amount)));
    }
}
