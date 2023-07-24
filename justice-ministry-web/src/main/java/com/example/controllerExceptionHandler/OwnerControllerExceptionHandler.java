package com.example.controllerExceptionHandler;

import com.example.controller.OwnerController;
import com.example.controller.OwnerPublicController;
import com.example.exception.IllegalOwnerPersonalNoException;
import com.example.exception.OwnerNotFoundException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice(basePackageClasses = {OwnerController.class, OwnerPublicController.class})
public class OwnerControllerExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(OwnerNotFoundException.class)
    public ResponseEntity<Object> handleOwnerNotFoundException(OwnerNotFoundException ex,
                                                               WebRequest req) {
        return handleExceptionInternal(ex, ex.getMessage(), new HttpHeaders(), HttpStatus.NOT_FOUND, req);
    }

    @ExceptionHandler(IllegalOwnerPersonalNoException.class)
    public ResponseEntity<Object> handleIllegalOwnerPersonalNoException(IllegalOwnerPersonalNoException ex,
                                                                        WebRequest req) {
        return handleExceptionInternal(ex, ex.getMessage(), new HttpHeaders(), HttpStatus.CONFLICT, req);
    }
}
