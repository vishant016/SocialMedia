package com.socialmedia.nicheplatform.userManagement.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

public class RegisterRequest {
    @Getter
    @NotBlank(message = "Name is required")
    private String name;

    @Getter
    @NotBlank(message = "Password is required")
    private String password;

    @Getter
    @Email(message = "Invalid email format")
    @NotBlank(message = "Email is required")
    private String email;

}


