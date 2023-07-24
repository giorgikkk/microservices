package com.example.controllerExceptionHandler;

import com.example.controller.InsuredCarImagesController;
import com.example.exception.ImageNotFoundException;
import com.example.exception.InsuredCarNotFoundException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice(basePackageClasses = InsuredCarImagesController.class)
public class InsuredCarImagesControllerExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(InsuredCarNotFoundException.class)
    public ResponseEntity<Object> handleCarNotFoundException(InsuredCarNotFoundException ex,
                                                             WebRequest req) {
        return handleExceptionInternal(ex, ex.getMessage(), new HttpHeaders(), HttpStatus.NOT_FOUND, req);
    }

    @ExceptionHandler(ImageNotFoundException.class)
    public ResponseEntity<Object> handleImageNotFoundException(ImageNotFoundException ex,
                                                               WebRequest req) {
        return handleExceptionInternal(ex, ex.getMessage(), new HttpHeaders(), HttpStatus.NOT_FOUND, req);
    }
}
