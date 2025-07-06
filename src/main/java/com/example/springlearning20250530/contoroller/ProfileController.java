package com.example.springlearning20250530.contoroller;

import com.example.springlearning20250530.entity.Profile;
import com.example.springlearning20250530.repository.ProfileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/profiles")
public class ProfileController {
    @Autowired
    private ProfileRepository profileRepository;

    @GetMapping
    public List<Profile> getAllProfiles() {
        return profileRepository.findAll();
    }

    @PostMapping
    public Profile createProfiles (@RequestBody Profile profile) {
        return profileRepository.save(profile);
    }
}
