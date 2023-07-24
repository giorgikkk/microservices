package com.example.controllerExceptionHandler;

import com.example.controller.InsuredCarController;
import com.example.exception.*;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice(basePackageClasses = InsuredCarController.class)
public class InsuredCarControllerExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(CarNotFoundException.class)
    public ResponseEntity<Object> handleCarNotFoundException(CarNotFoundException ex,
                                                             WebRequest req) {
        return handleExceptionInternal(ex, ex.getMessage(), new HttpHeaders(), HttpStatus.NOT_FOUND, req);
    }

    @ExceptionHandler(InsuredCarNotFoundException.class)
    public ResponseEntity<Object> handleCarNotFoundException(InsuredCarNotFoundException ex,
                                                             WebRequest req) {
        return handleExceptionInternal(ex, ex.getMessage(), new HttpHeaders(), HttpStatus.NOT_FOUND, req);
    }

    @ExceptionHandler(InsuranceAlreadyExistsException.class)
    public ResponseEntity<Object> handleInsuranceAlreadyExistsExceptionException(InsuranceAlreadyExistsException ex,
                                                                                 WebRequest req) {
        return handleExceptionInternal(ex, ex.getMessage(), new HttpHeaders(), HttpStatus.CONFLICT, req);
    }

    @ExceptionHandler(IllegalInsuranceException.class)
    public ResponseEntity<Object> handleIllegalInsuranceException(IllegalInsuranceException ex,
                                                                  WebRequest req) {
        return handleExceptionInternal(ex, ex.getMessage(), new HttpHeaders(), HttpStatus.NOT_ACCEPTABLE, req);
    }

    @ExceptionHandler(IllegalFranchiseAmountException.class)
    public ResponseEntity<Object> handleIllegalFranchiseAmountException(IllegalFranchiseAmountException ex,
                                                                        WebRequest req) {
        return handleExceptionInternal(ex, ex.getMessage(), new HttpHeaders(), HttpStatus.NOT_ACCEPTABLE, req);
    }
}
