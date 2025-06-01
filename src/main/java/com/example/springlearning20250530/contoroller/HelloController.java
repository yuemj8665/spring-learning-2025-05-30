package com.example.springlearning20250530.contoroller;

import com.example.springlearning20250530.entity.User;
import com.example.springlearning20250530.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class HelloController {
    @Autowired
    private UserRepository userRepository;

    @GetMapping("/hello")
    public String SayHello() {
        return "Hello fron Spring Boot with Gradle";
    }

    @GetMapping("/users")
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

}
