package com.example.springlearning20250530.contoroller;

import com.example.springlearning20250530.entity.User;
import com.example.springlearning20250530.repository.UserRepository;
import jakarta.servlet.http.HttpSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model; // 뷰에 전달하기 위한 모델 객체
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.Principal;

@Controller
public class LoginController {
    private static final Logger logger = LoggerFactory.getLogger(LoginController.class);
    private final UserRepository userRepository; // Userrepository 의존성

    @Autowired // 생성자 주입을 통해 Userrepository를 주입한다.
    public LoginController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @GetMapping("/login")
    public String login() {
        logger.info("[{}][{}]", "GET", "/login");
        return "login"; // src/main/resources/templates/login.html을 렌더링
    }

    @PostMapping("/login")
    public String login(@RequestParam String username, @RequestParam String password, Model model, HttpSession session) {
        logger.info("[{}][{}][{}][{}]", "POST", "/login", username, password);
        User user = userRepository.findByUsername(username);
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        logger.info("user info... : " + user.toString());
        if (user != null && encoder.matches(password, user.getPassword())) {
            session.setAttribute("username", username);
            model.addAttribute("message", "Login Successful for " + username);
            return "redirect:/welcome";
        } else {
            model.addAttribute("error", "Invalid username or password");
            return "login";
        }
    }

    @GetMapping("/welcome")
    public String welcome(Model model, HttpSession session, Principal principal) {
        if (principal != null) {
            String username = principal.getName();
            model.addAttribute("message", "Login Successful for " + username);
        } else {
            model.addAttribute("message", "Welcome GUEST!");
        }
        return "welcome";
    }
}
