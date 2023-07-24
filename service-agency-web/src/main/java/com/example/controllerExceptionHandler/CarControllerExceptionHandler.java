package com.example.controllerExceptionHandler;

import com.example.controller.CarController;
import com.example.controller.CarPublicController;
import com.example.exception.*;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice(basePackageClasses = {CarController.class, CarPublicController.class})
public class CarControllerExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(CarNotFoundException.class)
    public ResponseEntity<Object> handleCarNotFoundException(CarNotFoundException ex,
                                                             WebRequest req) {
        return handleExceptionInternal(ex, ex.getMessage(), new HttpHeaders(), HttpStatus.NOT_FOUND, req);
    }

    @ExceptionHandler(OwnerNotFoundException.class)
    public ResponseEntity<Object> handleOwnerNotFoundException(OwnerNotFoundException ex,
                                                               WebRequest req) {
        return handleExceptionInternal(ex, ex.getMessage(), new HttpHeaders(), HttpStatus.NOT_FOUND, req);
    }

    @ExceptionHandler(RegistrationNotFound.class)
    public ResponseEntity<Object> handleRegistrationNotFoundException(RegistrationNotFound ex,
                                                                      WebRequest req) {
        return handleExceptionInternal(ex, ex.getMessage(), new HttpHeaders(), HttpStatus.NOT_FOUND, req);
    }

    @ExceptionHandler(IllegalCarLicensePlateException.class)
    public ResponseEntity<Object> handleIllegalCarLicensePlateException(IllegalCarLicensePlateException ex,
                                                                        WebRequest req) {
        return handleExceptionInternal(ex, ex.getMessage(), new HttpHeaders(), HttpStatus.CONFLICT, req);
    }

    @ExceptionHandler(IllegalVinCodeException.class)
    public ResponseEntity<Object> handleIllegalCarVinCodeException(IllegalVinCodeException ex,
                                                                   WebRequest req) {
        return handleExceptionInternal(ex, ex.getMessage(), new HttpHeaders(), HttpStatus.CONFLICT, req);
    }
}
