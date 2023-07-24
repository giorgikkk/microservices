package com.example.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.CONFLICT, reason = "car vin code already exists")
public class IllegalVinCodeException extends RuntimeException {
    public IllegalVinCodeException(String message) {
        super(message);
    }
}
