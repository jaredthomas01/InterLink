package com.example.InterLink.controller;

import com.example.InterLink.dto.LoginRequest;
import com.example.InterLink.entity.UserEntity;
import com.example.InterLink.service.EmailService;
import com.example.InterLink.service.UserService;
import com.example.InterLink.service.VerificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController

@RequestMapping("/users")
public class AuthController {

    @Autowired
    private UserService userService;

    @Autowired
    private EmailService emailService;

    @Autowired
    private VerificationService verificationService;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {
        Optional<UserEntity> userOpt = userService.findByEmail(loginRequest.getEmail());

        if (userOpt.isPresent()) {
            UserEntity user = userOpt.get();
            if (user.getPassword().equals(loginRequest.getPassword())) {

                // 👉 Role-based redirection suggestion:
                String role = user.getRole().toString().toLowerCase();
                String redirectUrl;

                switch (role) {
                    case "student":
                        redirectUrl = "/student/dashboard";
                        break;
                    case "coordinator":
                        redirectUrl = "/coordinator/dashboard";
                        break;
                    case "company representative":
                        redirectUrl = "/company/dashboard";
                        break;
                    default:
                        redirectUrl = "/unknown-role";
                }

                // ✅ Add ID to the response so frontend can store it
                return ResponseEntity.ok().body(
                        Map.of(
                                "message", "Login successful",
                                "id", user.getId(),
                                "email", user.getEmail(),
                                "role", role,
                                "redirect", redirectUrl
                        )
                );

            } else {
                return ResponseEntity.status(401).body("Invalid password");
            }
        } else {
            return ResponseEntity.status(404).body("User not found");
        }
    }

}
//
//    @PostMapping("/forgot-password")
//    public ResponseEntity<String> forgotPassword(@RequestBody Map<String, String> payload) {
//        String email = payload.get("email");
//        Optional<UserEntity> userOpt = userService.findByEmail(email);
//
//        if (userOpt.isPresent()) {
//            String code = verificationService.generateCodeForUser(email);
////            emailService.sendVerificationCode(email, code);
//            return ResponseEntity.ok("Verification code sent.");
//        } else {
//            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found.");
//        }
//    }
//}
