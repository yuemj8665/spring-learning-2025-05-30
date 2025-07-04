package com.example.springlearning20250530.handler;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component // 스프링 빈으로 등록
public class MyAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    private static final Logger logger = LoggerFactory.getLogger(MyAuthenticationSuccessHandler.class);

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException {
        logger.info("Custom AuthenticationSuccessHandler: Login successful for user: {}", authentication.getName());

        // 여기에 원하는 리디렉션 로직을 구현합니다.
        // 예를 들어, 역할에 따라 다른 페이지로 리디렉션할 수 있습니다.
        if (authentication.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"))) {
            response.sendRedirect("/admin/dashboard"); // 관리자라면 다른 페이지로
        } else {
            response.sendRedirect("/welcome"); // 일반 사용자라면 /welcome으로
        }

        // 또는 항상 특정 페이지로 리디렉션
        // response.sendRedirect("/welcome");
    }
}
