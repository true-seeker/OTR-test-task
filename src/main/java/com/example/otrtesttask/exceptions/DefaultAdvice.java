package com.example.otrtesttask.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

@ControllerAdvice
public class DefaultAdvice {

    @ExceptionHandler({CustomApiException.class})
    public ResponseEntity<Response> handleException(CustomApiException e) {
        Response response = new Response(e.getMessage(), e.getStatus());
        return new ResponseEntity<>(response, e.getStatus());
    }
}