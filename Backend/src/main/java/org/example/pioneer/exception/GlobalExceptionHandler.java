package org.example.pioneer.exception;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<String> handleDataIntegrityViolationException(DataIntegrityViolationException ex) {
        // Extrae el mensaje para que sea más entendible
        String message = "Duplicate key violation: " + ex.getRootCause().getMessage();
        if (message.contains("email")) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Email already exists. Please use a different email.");
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Database constraint violation: " + message);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleGenericException(Exception ex) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An unexpected error occurred.");
    }
}
