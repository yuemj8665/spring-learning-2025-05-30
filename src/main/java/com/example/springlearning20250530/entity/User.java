package com.example.springlearning20250530.entity;

import jakarta.persistence.*;

@Entity
@Table (name = "users")
public class User {

    @Id // id가 이 테이블의 primary key라는걸 선언
    @GeneratedValue(strategy = GenerationType.IDENTITY) // 키값이 자동증가하도록 생성
    private Long id;
    @Column(unique = true, nullable = false)
    private String username;
    @Column(nullable = false)
    private String password;
    @Column(unique = true)
    private String email;
    @Column(nullable = false)
    private String createdAt; // 사용자 생성 날짜

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }
}
