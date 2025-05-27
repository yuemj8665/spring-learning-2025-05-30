package com.example.springlearning20250530;

import org.springframework.web.bind.annotation.GetMapping;

public class HelloworldContoller {
    @GetMapping("/hello")
    public String sayHello() {
        return "Hello from Spring Boot!";
    }
}
