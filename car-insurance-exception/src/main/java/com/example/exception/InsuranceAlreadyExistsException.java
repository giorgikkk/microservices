package com.example.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.CONFLICT, reason = "insurance already exists")
public class InsuranceAlreadyExistsException extends RuntimeException {
    public InsuranceAlreadyExistsException(String message) {
        super(message);
    }
}
