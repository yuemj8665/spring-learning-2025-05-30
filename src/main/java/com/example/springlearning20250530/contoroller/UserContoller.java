package com.example.springlearning20250530.contoroller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/userss")
public class UserContoller {
    @GetMapping
    @PreAuthorize("hasRole('USER')")
    public String getUser() {
        return "UserController::userss::User data";
    }
}
