package com.devsuperior.bds02.controllers.exceptionHandlers;

import com.devsuperior.bds02.dto.errors.CustomError;
import com.devsuperior.bds02.service.exceptions.DatabaseException;
import com.devsuperior.bds02.service.exceptions.ResourceNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.Instant;

@ControllerAdvice
public class ControllerExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<CustomError> resourceNotFound(ResourceNotFoundException e, HttpServletRequest request) {
        HttpStatus status = HttpStatus.NOT_FOUND;
        CustomError error = new CustomError(
                Instant.now(), status.value(), e.getMessage(), request.getRequestURI());
        return ResponseEntity.status(status).body(error);
    }

    @ExceptionHandler(DatabaseException.class)
    public ResponseEntity<CustomError> database(DatabaseException e, HttpServletRequest request) {
        HttpStatus status = HttpStatus.BAD_REQUEST;
        CustomError error = new CustomError(
                Instant.now(), status.value(), e.getMessage(), request.getRequestURI());
        return ResponseEntity.status(status).body(error);
    }
}
