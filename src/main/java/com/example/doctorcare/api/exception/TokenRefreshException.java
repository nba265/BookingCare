package com.example.doctorcare.api.exception;

public class TokenRefreshException extends RuntimeException{
    public TokenRefreshException(String message) {
        super(message);
    }
}
