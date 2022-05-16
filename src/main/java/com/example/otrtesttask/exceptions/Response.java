package com.example.otrtesttask.exceptions;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.http.HttpStatus;

import java.util.Date;

@Data
public class Response {
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy hh:mm:ss")

    private String error;
    private Date timestamp;
    private Integer code;
    private String status;

    public Response() {
        timestamp = new Date();
    }

    public Response(String message, HttpStatus httpStatus) {
        this();
        this.code = httpStatus.value();
        this.status = httpStatus.name();
        this.error = message;
    }
}