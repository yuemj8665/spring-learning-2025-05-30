package com.example.springlearning20250530.contoroller;

import com.example.springlearning20250530.dto.UserDto;
import com.example.springlearning20250530.entity.User;
import com.example.springlearning20250530.repository.UserRepository;
import com.example.springlearning20250530.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class HelloController {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserService userService;

    @GetMapping("/hello")
    public String SayHello() {
        userService.processLongTask(); // 비동기 호출한다.
        return "Hello fron Spring Boot with Gradle ( Async Task Started )";
    }

    @GetMapping("/users")
    @Cacheable(value = "usersCache", key = "'allUsers'")
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @PostMapping("/users")
    public ResponseEntity<User> createUser(@RequestBody UserDto userDto) {
        // 데이터 유효성 검사
        if (userDto.getUsername() == null ||
                userDto.getUsername().trim().isEmpty() ||
                userDto.getEmail() == null ||
                userDto.getEmail().trim().isEmpty()) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST); // 400 ERROR
        }
        try {
            User user = new User();
            user.setUsername(userDto.getUsername());
            user.setEmail(userDto.getEmail());
            // DB에 저장 후 usersave로 반환
            User usersave = userRepository.save(user);
            return new ResponseEntity<>(usersave, HttpStatus.CREATED); // 정상이라면 201 CREATED
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR); // 에러뜨면 걍 500 INTERNAL SERVER ERROR
        }
    }

    @PutMapping("/users/{id}")
    public ResponseEntity<User> updateUser(@PathVariable Long id, @RequestBody UserDto userDto) {
        // Optional로 null 처리 가능성 대비
        Optional<User> userOptional = userRepository.findById(id);
        if (!userOptional.isPresent()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND); // 못찾는다면 404 NOT FOUND로 반환.
        }

        try {
            // 데이터 유효성 검사
            User user = userOptional.get();
            if (userDto.getUsername() == null ||
                    userDto.getUsername().trim().isEmpty() ||
                    userDto.getEmail() == null ||
                    userDto.getEmail().trim().isEmpty()) {
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST); // 400 ERROR
            }
            user.setUsername(userDto.getUsername());
            user.setEmail(userDto.getEmail());
            User updateUser = userRepository.save(user);
            return new ResponseEntity<>(updateUser, HttpStatus.OK); // OK
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR); // 500 ERROR
        }
    }

    @DeleteMapping("/users/{id}")
    public ResponseEntity deleteUser(@PathVariable Long id) {
        // 존재 확인
        if (!userRepository.existsById(id)) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        try {
            userRepository.deleteById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT); // 204
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
