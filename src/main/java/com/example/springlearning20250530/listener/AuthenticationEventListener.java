package com.example.springlearning20250530.listener;

import com.example.springlearning20250530.contoroller.HelloController;
import org.apache.commons.logging.LogFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.event.EventListener;
import org.springframework.security.authentication.event.AbstractAuthenticationFailureEvent;
import org.springframework.security.authentication.event.AuthenticationSuccessEvent;
import org.springframework.stereotype.Component;

@Component
public class AuthenticationEventListener {
    private static final Logger logger = LoggerFactory.getLogger(AuthenticationEventListener.class);

    @EventListener
    public void handleAuthenticationSuccess(AuthenticationSuccessEvent event) {
        String username = event.getAuthentication().getName();
        Object password = event.getAuthentication().getCredentials(); // PASSWORD
        String enteredPassword = (password != null) ? password.toString() : "N/A";
        logger.info("로그인을 성공함 : '{}', PASSWORD : '{}'", username, enteredPassword);
    }

    @EventListener
    public void handlerAuthenticationFailure(AbstractAuthenticationFailureEvent event) {
        String username = (String) event.getAuthentication().getPrincipal(); // 로그인 시도한 사용자명 (보통 Principal)
        Object password = event.getAuthentication().getCredentials(); // PASSWORD
        String enteredPassword = (password != null) ? password.toString() : "N/A";
        String errorMessage = event.getException().getMessage(); // 실패 원인 메시지
        logger.warn("로그인 실패! 사용자: '{}', 원인: {}, password : '{}'", username, errorMessage, enteredPassword);
    }
}
