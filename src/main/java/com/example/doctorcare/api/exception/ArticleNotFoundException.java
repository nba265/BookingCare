package com.example.doctorcare.api.exception;

public class ArticleNotFoundException extends RuntimeException{
    public ArticleNotFoundException(String message) {
        super(message);
    }
}
