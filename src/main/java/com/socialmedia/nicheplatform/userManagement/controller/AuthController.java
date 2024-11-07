package com.socialmedia.nicheplatform.userManagement.controller;

import com.socialmedia.nicheplatform.notification.EmailService;
import com.socialmedia.nicheplatform.userManagement.dto.LoginRequest;
import com.socialmedia.nicheplatform.userManagement.dto.RegisterRequest;
import com.socialmedia.nicheplatform.userManagement.model.User;
import com.socialmedia.nicheplatform.userManagement.model.VerificationToken;
import com.socialmedia.nicheplatform.userManagement.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Timestamp;
import java.util.Date;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private UserService userService;
    @Autowired
    private EmailService emailService;

    @PostMapping("/register")
    public ResponseEntity<String> registerUser(@RequestBody RegisterRequest request) {
        try {
            User user=userService.registerUser(request.getEmail(), request.getPassword(), request.getName());

            // Generate verification token and send email
            String token = userService.generateVerificationToken(user);
            String verificationUrl = "http://localhost:8080/api/auth/verify?token=" + token;
            emailService.sendVerificationEmail(user.getEmail(), verificationUrl);

            return ResponseEntity.ok("\"User registered successfully. Check your email for verification.\"");
        } catch (RuntimeException ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
        }
    }


    @PostMapping("/login")
    public ResponseEntity<String> loginUser(@RequestBody LoginRequest loginRequest) {
        try {
             userService.loginUser(loginRequest.getEmail(), loginRequest.getPassword());
             return ResponseEntity.ok("\"Login Successfully\"");
        }catch (RuntimeException ex) {
           return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
        }

    }

    @GetMapping("/verify")
    public ResponseEntity<String> verifyAccount(@RequestParam("token") String token) {
        try {
            VerificationToken verificationToken = userService.getVerificationToken(token);
            if (verificationToken == null || verificationToken.getExpiryDate().before(new Date(System.currentTimeMillis()))) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid or expired token.");
            }

            User user = verificationToken.getUser();
            user.setIsVerified(true);
            userService.save(user);

            userService.deleteVerificationToken(verificationToken); // Remove token after verification

            return ResponseEntity.ok("Account verified successfully.");
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

}
