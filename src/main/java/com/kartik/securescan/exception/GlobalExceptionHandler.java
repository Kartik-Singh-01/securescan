package com.kartik.securescan.exception;

import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ScanNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleScanNotFound(
            ScanNotFoundException ex) {

        ErrorResponse error = new ErrorResponse();

        error.setTimestamp(LocalDateTime.now());
        error.setStatus(HttpStatus.NOT_FOUND.value());
        error.setError("Scan Not Found");
        error.setMessage(ex.getMessage());

        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }

}