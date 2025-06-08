package com.example.springlearning20250530.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

// Spring Security
@Configuration
@EnableWebSecurity
public class SecurityConfig {
    // 보안 필터 체인 설정
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests((requests) -> requests
                        .requestMatchers("/hello").permitAll() // hello는 인증 없이 접근 가능함
                        .anyRequest().authenticated() // 나머지는 인증 필요하게 바꾼다.
                )
                // .httpBasic() 추후에 삭제 될 예정으로 formLogin을 사용한다.
                // 기본 인증 대체
                .formLogin((form) -> form
                        .loginProcessingUrl("/login") // 로그인 처리 URL
                        .permitAll() // 로그인 페이지 접근 혀용
                )
                .logout((logout) -> logout.permitAll()); // 로그아웃 허용
        return http.build();
    }
}
