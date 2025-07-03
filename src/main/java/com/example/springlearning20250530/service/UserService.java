package com.example.springlearning20250530.service;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Async
    public void processLongTask() {
        try {
            Thread.sleep(5000); // 5초 대기
            System.out.println("Long Task completed by thread : " + Thread.currentThread().getName() );
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
