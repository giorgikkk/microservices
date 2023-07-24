package com.example.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_ACCEPTABLE, reason = "franchise amount should be 1% of full insurance amount")
public class IllegalFranchiseAmountException extends RuntimeException {
    public IllegalFranchiseAmountException(String message) {
        super(message);
    }
}
