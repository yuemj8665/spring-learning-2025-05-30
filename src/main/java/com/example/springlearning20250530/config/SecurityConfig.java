package com.example.springlearning20250530.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;

// Spring Security
@Configuration
@EnableWebSecurity
public class SecurityConfig {

    // BCrypt 인코더 사용하여 암호화 객체를 생성한다.
    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    // 사용자 인증 정보 제공 빈 등록
    @Bean
    public UserDetailsService userDetailsService(BCryptPasswordEncoder passwordEncoder) {
        InMemoryUserDetailsManager manager = new InMemoryUserDetailsManager();
        String endcodedPassword = passwordEncoder.encode("admin");
        UserDetails user = User.withUsername("admin")
                .password(endcodedPassword)
                .roles("USER")
                .build();
        manager.createUser(user);
        return manager;
    }
    // 보안 필터 체인 설정
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests((authorize) -> authorize
                        .requestMatchers(
                                "/swagger-ui.html",
                                "/swagger-ui/**", // Swagger UI 정적 리소스
                                "/v3/api-docs/**", // OpenAPI 3 문서
                                "/v2/api-docs/**", // Swagger 2 문서 (사용하는 경우)
                                "/webjars/**",    // Swagger UI가 사용하는 웹자(webjars) 리소스
                                "/swagger-resources/**" // Swagger 리소스
                        ).permitAll() // Swagger 및 OpenAPI 허용
                        .requestMatchers("/").permitAll()
                        .requestMatchers("/login").permitAll()
                        .requestMatchers("/welcome").hasRole("USER")
                        .requestMatchers("/users").hasRole("USER")
                        .anyRequest().authenticated()
                )
                .formLogin((form) -> form
                        .loginPage("/login")
                        .defaultSuccessUrl("/welcome", true) // 성공 시 /welcome으로 강제 리다이렉션
                        .permitAll()
                )
                .csrf((csrf) -> csrf
                        .disable() // 초보용 단순화
                );
        return http.build();
    }
}
