package com.example.springlearning20250530.repository;

import com.example.springlearning20250530.entity.Profile;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProfileRepository extends JpaRepository<Profile, Long> {
    // 기본 CRUD 메소드를 제공한다.
}
