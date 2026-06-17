package com.exam.util;

import java.util.Map;

public class ErrorResponse {
    private Map<String,String> errors;

    private String message;

    public ErrorResponse() {
    }

    public ErrorResponse(Map<String, String> errors, String message) {
        this.errors = errors;
        this.message = message;
    }

    public Map<String, String> getErrors() {
        return errors;
    }

    public void setErrors(Map<String, String> errors) {
        this.errors = errors;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
