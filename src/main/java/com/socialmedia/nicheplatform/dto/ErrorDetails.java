package com.socialmedia.nicheplatform.dto;

import java.util.List;

public class ErrorDetails {
    private String errorCode;
    private String errorMessage;
    private List<String> errors;  // Detailed errors (optional, can be null)

    public ErrorDetails(String errorCode, String errorMessage, List<String> errors) {
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
        this.errors = errors;
    }
}

