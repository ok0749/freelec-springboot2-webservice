package com.jojoldu.book.springboot.config.auth;

import com.jojoldu.book.springboot.domain.user.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@RequiredArgsConstructor
// Spring Security 설정들을 활성화한다.
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    private final CustomOAuth2UserService customOAuth2UserService;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                // h2-console을 화면을 사용하기 위해 해당 옵션들을 disable 한다.
                .csrf().disable()
                .headers().frameOptions().disable()
                .and()
                // url별 권환 관리를 실행하는 옵션의 시작점
                // authorizeRequests가 선언되야만 andMatchers 옵션을 사용할 수 있다.
                .authorizeRequests()
                // 권환 관리 대상을 설정하는 옵션
                // url, http 메소드별로 관리할 수 있다.
                // '/'등 지정한 url들은 permitAll() 옵션을 통해 전체 열람 권한을 주었다.
                .antMatchers("/", "/css/**", "/images/**", "/js/**", "/h2-console/**", "/posts/**").permitAll()
                // 'api/v1/**' 주소를 가진 api는 user 권한을 가진 사람만 가능하도록 했다.
                .antMatchers("/api/v1/**")
                .hasRole(Role.USER.name())
                // 설정된 값들 이외 나머지 url들을 나타낸다.
                .anyRequest()
                // authenticated()를 추가하여 인증된 사용자에게만 허용한다.
                .authenticated()
                .and()
                // 로그아웃 기능에 대한 여러 설정의 진입점
                .logout()
                // 로그아웃 성공시 '/' 주소로 이동한다.
                .logoutSuccessUrl("/")
                .and()
                // OAuth2 로그인 기능에 대한 여러 설정의 진입점
                .oauth2Login()
                // OAuth2 로그인 성공 이후 사용자 정보를 가져올 때의 설정들을 담당
                .userInfoEndpoint()
                // 소셜 로그인 성공 시 후속 조치를 진행할 UserService 인터페이스의 구현체를 등록한다.
                // 리소스 서버(소셜 서비스들)에서 사용자 정보를 가져온 상태에서
                // 추가로 진행하고자 하는 기능을 명시할 수 있다.
                .userService(customOAuth2UserService);
    }
}