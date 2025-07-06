package com.example.springlearning20250530.contoroller;

import com.example.springlearning20250530.entity.User;
import com.example.springlearning20250530.repository.UserRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
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

@Tag(name = "인증 및 로그인", description = "로그인 및 사용자 인증 관련 API")
@Controller
public class LoginController {
    private static final Logger logger = LoggerFactory.getLogger(LoginController.class);
    private final UserRepository userRepository; // Userrepository 의존성

    @Autowired // 생성자 주입을 통해 Userrepository를 주입한다.
    public LoginController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Operation(summary = "로그인 페이지 표시", description = "사용자가 로그인 할 수 있는 HTML 페이지")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "성공적으로 로그인 페이지 반환" ,content = @Content(mediaType = "text/html"))
    })
    @GetMapping("/login")
    public String login() {
        logger.info("[{}][{}]", "GET", "/login");
        return "login"; // src/main/resources/templates/login.html을 렌더링
    }

    @Operation(summary = "로그인 구현", description = "사용자가 로그인 요청을 보내는 API")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "302", description = "로그인 성공 시 /welcome으로 리다이렉션"),
            @ApiResponse(responseCode = "200", description = "로그인 실패 시 /login으로 다시 진입", content = @Content(mediaType = "text/html")),
    })
    @PostMapping("/login")
    public String login(
            @Parameter (description = "사용자 계정", required = true) @RequestParam String username,
            @Parameter (description = "패스워드", required = true)@RequestParam String password,
            Model model,
            HttpSession session) {
        logger.info("[{}][{}][{}][{}]", "POST", "/login", username, password);
        User user = userRepository.findByUsername(username);
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        // User null 체크
        if (user == null) {
            logger.warn("login fail... : user {} not found", username);
            model.addAttribute("error", "invalid user or password");
            return "login";
        }

        if (username != null && encoder.matches(password, user.getPassword())) {
            session.setAttribute("username", username);
            model.addAttribute("message", "Login Successful for " + username);
            return "redirect:/welcome";
        } else {
            model.addAttribute("error", "Invalid username or password");
            return "login";
        }
    }

    @Operation(summary = "로그인 성공 후 페이지 표시", description = "사용자가 로그인 성공 후 진입하는 페이지")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "성공적으로 로그인 성공 페이지 반환" ,content = @Content(mediaType = "text/html"))
    })
    @GetMapping("/welcome")
    public String welcome(Model model, Principal principal) {
        if (principal != null) {
            String username = principal.getName();
            model.addAttribute("message", "Login Successful for " + username);
        } else {
            model.addAttribute("message", "Welcome GUEST!");
        }
        return "welcome";
    }
}
