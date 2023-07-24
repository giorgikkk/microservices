package com.example.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_ACCEPTABLE, reason = "car over 25 years can not be insured")
public class IllegalInsuranceException extends RuntimeException {
    public IllegalInsuranceException(String message) {
        super(message);
    }
}
