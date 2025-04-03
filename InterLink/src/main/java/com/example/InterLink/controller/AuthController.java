package com.example.InterLink.controller;

import com.example.InterLink.entity.UserEntity;
import com.example.InterLink.service.EmailService;
import com.example.InterLink.service.UserService;
import com.example.InterLink.service.VerificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private UserService userService;

    @Autowired
    private EmailService emailService;

    @Autowired
    private VerificationService verificationService;

    @PostMapping("/forgot-password")
    public ResponseEntity<String> forgotPassword(@RequestBody Map<String, String> payload) {
        String email = payload.get("email");
        Optional<UserEntity> userOpt = userService.findByEmail(email);

        if (userOpt.isPresent()) {
            String code = verificationService.generateCodeForUser(email);
//            emailService.sendVerificationCode(email, code);
            return ResponseEntity.ok("Verification code sent.");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found.");
        }
    }
}
