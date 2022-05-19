package com.example.otrtesttask.exceptions;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.http.HttpStatus;

import java.util.Date;

@Data
public class Response {
    // Ответ, который возвращается при получении ошибки от API
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy hh:mm:ss")

    private String error;
    private Date timestamp;
    private Integer code;
    private String status;

    public Response(String message, HttpStatus httpStatus) {
        timestamp = new Date();

        this.code = httpStatus.value();
        this.status = httpStatus.name();
        this.error = message;
    }
}