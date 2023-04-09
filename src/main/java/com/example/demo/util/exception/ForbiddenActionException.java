package com.example.demo.util.exception;

import org.springframework.http.HttpStatus;

public class ForbiddenActionException extends RuntimeException {
    private final HttpStatus status;

    public ForbiddenActionException(String message, HttpStatus status) {
        super(message);
        this.status = status;
    }
}
