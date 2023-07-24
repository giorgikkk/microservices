package com.example.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.CONFLICT, reason = "car license plate already exists")
public class IllegalCarLicensePlateException extends RuntimeException {
    public IllegalCarLicensePlateException(String message) {
        super(message);
    }
}
