package com.example.demo.util.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class ConditionsNotMetException extends RuntimeException {
    private final HttpStatus status;

    public ConditionsNotMetException(String message, HttpStatus status) {
        super(message);
        this.status = status;
    }
}
