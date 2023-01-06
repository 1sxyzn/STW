package com.website.stw;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.header.writers.frameoptions.XFrameOptionsHeaderWriter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration // 환경설정 파일임을 나타냄
@EnableWebSecurity // 모든 요청 URL이 스프링 시큐리티의 제어를 받음
public class SecurityConfig {
    @Bean
    SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests().requestMatchers(
                new AntPathRequestMatcher("/**")).permitAll() // 인증되지 않은 요청 허락
                .and()
                    .csrf().ignoringRequestMatchers(
                        new AntPathRequestMatcher("/h2-console/**")) // CSRF 처리시 H2 콘솔은 예외 처리
                .and()
                    .headers()
                    .addHeaderWriter(new XFrameOptionsHeaderWriter(
                        XFrameOptionsHeaderWriter.XFrameOptionsMode.SAMEORIGIN)); // Frame 구조로 작성된 H2 화면 오류 해결을 위해..
        return http.build();
    }
}