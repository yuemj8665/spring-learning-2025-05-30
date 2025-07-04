package com.example.springlearning20250530.repository;

import com.example.springlearning20250530.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsername(String username);
}
