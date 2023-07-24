package com.example.exception;


import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND, reason = "insured car not found")
public class InsuredCarNotFoundException extends RuntimeException {
    public InsuredCarNotFoundException(String message) {
        super(message);
    }
}
