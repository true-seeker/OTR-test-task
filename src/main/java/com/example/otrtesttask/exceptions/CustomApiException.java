package com.example.otrtesttask.exceptions;

import lombok.Data;
import org.springframework.http.HttpStatus;
@Data
public class CustomApiException extends Exception{
    private HttpStatus status;
    // Исключение, в котором передается ошибка при обращении к API и её код
    public CustomApiException(String message, HttpStatus httpStatus) {
        super(message);
        this.status = httpStatus;
    }
}