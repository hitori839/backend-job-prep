package com.example.backend.common.error;

import java.util.Map;

public class ValidationErrorResponse {

    private final String message;
    private final Map<String, String> errors;

    public ValidationErrorResponse(String message, Map<String, String> errors) {
        this.message = message;
        this.errors = errors;
    }

    public String getMessage() {
        return message;
    }

    public Map<String, String> getErrors() {
        return errors;
    }
}
