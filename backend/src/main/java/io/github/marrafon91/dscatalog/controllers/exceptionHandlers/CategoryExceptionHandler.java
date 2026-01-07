package io.github.marrafon91.dscatalog.controllers.exceptionHandlers;

import io.github.marrafon91.dscatalog.dto.errors.CustomError;
import io.github.marrafon91.dscatalog.services.exceptions.ResourceNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.Instant;

@ControllerAdvice
public class CategoryExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<CustomError> resourceNotFound(ResourceNotFoundException e, HttpServletRequest request) {
        HttpStatus status = HttpStatus.NOT_FOUND;
        CustomError error = new CustomError(
                Instant.now(), status.value(), e.getMessage()
                ,"Entity not found", request.getRequestURI());
        return ResponseEntity.status(status).body(error);
    }
}
