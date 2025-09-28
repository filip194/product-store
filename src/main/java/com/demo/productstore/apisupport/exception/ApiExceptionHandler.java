package com.demo.productstore.apisupport.exception;


import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
public class ApiExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ApiResponse<Void>> resourceNotFoundExceptionHandler(ResourceNotFoundException ex) {
        log.error("Resource not found", ex);
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(ApiResponse.withError(ex.getMessage(), String.valueOf(HttpStatus.NOT_FOUND.value())));
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ApiResponse<Void>> illegalArgumentExceptionHandler(IllegalArgumentException ex) {
        log.error("Bad request", ex);
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(ApiResponse.withError(ex.getMessage(), String.valueOf(HttpStatus.BAD_REQUEST.value())));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse<Void>> globalExceptionHandler(Exception ex) {
        log.error("Unexpected exception", ex);
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(ApiResponse.withError(ex.getMessage(), String.valueOf(HttpStatus.INTERNAL_SERVER_ERROR.value())));
    }

    @ExceptionHandler(Error.class)
    public ResponseEntity<ApiResponse<Void>> globalErrorHandler(Error e) {
        log.error("Unexpected error", e);
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(ApiResponse.withError(e.getMessage(), String.valueOf(HttpStatus.INTERNAL_SERVER_ERROR.value())));
    }
}
