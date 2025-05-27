package com.example.springlearning20250530;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloworldContoller {
    @GetMapping("/hello")
    public String sayHello() {
        return "Hello from Spring Boot! You are using Spring Boot!";
    }
}
