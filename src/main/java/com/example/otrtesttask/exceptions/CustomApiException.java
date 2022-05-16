package com.example.otrtesttask.exceptions;

import lombok.Data;
import org.springframework.http.HttpStatus;
@Data
public class CustomApiException extends Exception{
    private HttpStatus status;
    public CustomApiException(String message, HttpStatus httpStatus) {
        super(message);
        this.status = httpStatus;
    }
}