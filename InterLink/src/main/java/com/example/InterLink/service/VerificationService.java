package com.example.InterLink.service;

import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class VerificationService {

    private final Map<String, String> verificationCodes = new HashMap<>();

    public String generateCodeForUser(String email) {
        String code = String.valueOf((int)(Math.random() * 900000) + 100000);
        verificationCodes.put(email, code);
        return code;
    }

    public boolean validate(String email, String code) {
        return verificationCodes.containsKey(email) && verificationCodes.get(email).equals(code);
    }
}
