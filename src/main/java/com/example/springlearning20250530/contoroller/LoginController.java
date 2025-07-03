package com.example.springlearning20250530.contoroller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LoginController {
    private static final Logger logger = LoggerFactory.getLogger(LoginController.class);
    @GetMapping("/login")
    public String login() {
        logger.info("LoginController::###################");
        return "login"; // src/main/resources/templates/login.html을 렌더링
    }
}
