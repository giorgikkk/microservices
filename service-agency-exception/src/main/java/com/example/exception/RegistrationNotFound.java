package com.example.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND, reason = "registration not found")
public class RegistrationNotFound extends RuntimeException {
    public RegistrationNotFound(String message) {
        super(message);
    }
}
