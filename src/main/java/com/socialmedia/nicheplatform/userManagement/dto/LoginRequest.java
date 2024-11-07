package com.socialmedia.nicheplatform.userManagement.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

public class LoginRequest {
    @Getter
    @Email(message = "Invalid email format")
    @NotBlank(message = "Email is required")
    private String email;

    @Getter
    @NotBlank(message = "Password is required")
    private String password;

    // Getters and Setters
}
