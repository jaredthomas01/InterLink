package com.example.InterLink.controller;

import com.example.InterLink.entity.UserEntity;
import com.example.InterLink.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public UserEntity createUser(@RequestBody UserEntity userEntity) {
        return userService.saveUser(userEntity);
    }

    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@RequestBody UserEntity loginRequest) {
        Optional<UserEntity> userOpt = userService.findByEmail(loginRequest.getEmail());

        if (userOpt.isPresent()) {
            UserEntity user = userOpt.get();
            if (user.getPassword().equals(loginRequest.getPassword())) {
                return ResponseEntity.ok(user);
            } else {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid password");
            }
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserEntity> getUserById(@PathVariable Long id) {
        Optional<UserEntity> user = userService.getUserById(id);
        return user.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @GetMapping
    public ResponseEntity<List<UserEntity>> getAllUsers() {
        return new ResponseEntity<>(userService.getAllUsers(), HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserEntity> updateUser(@PathVariable Long id, @RequestBody UserEntity userEntity) {
        UserEntity updatedUser = userService.updateUser(id, userEntity);
        return updatedUser != null ? new ResponseEntity<>(updatedUser, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}