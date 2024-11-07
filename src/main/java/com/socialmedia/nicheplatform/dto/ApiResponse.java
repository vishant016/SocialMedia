package com.socialmedia.nicheplatform.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ApiResponse<T> {
    private boolean success;
    private String message;
    private T data;
    private ErrorDetails error;
    private LocalDateTime timestamp;

    // Constructors for various scenarios
    public ApiResponse(boolean success, String message, T data) {
        this.success = success;
        this.message = message;
        this.data = data;
        this.timestamp = LocalDateTime.now();
    }

    public ApiResponse(boolean success, String message, ErrorDetails error) {
        this.success = success;
        this.message = message;
        this.error = error;
        this.timestamp = LocalDateTime.now();
    }
}

