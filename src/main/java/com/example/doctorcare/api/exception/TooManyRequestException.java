package com.example.doctorcare.api.exception;

public class TooManyRequestException extends RuntimeException{
    public TooManyRequestException(String message) {
        super(message);
    }
}
