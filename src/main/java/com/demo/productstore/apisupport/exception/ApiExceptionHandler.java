package com.demo.productstore.apisupport.exception;


import com.demo.productstore.product.exception.ProductAlreadyExistsException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * Handles exceptions globally for the application.
 */
@RestControllerAdvice
@Slf4j
public class ApiExceptionHandler {

    /**
     * Handles ResourceNotFoundException and returns a 404 Not Found response with an error message.
     *
     * @param ex the ResourceNotFoundException instance
     * @return a ResponseEntity containing an ApiResponse with the error details
     */
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ApiResponse<Void>> resourceNotFoundExceptionHandler(ResourceNotFoundException ex) {
        log.error("Resource not found", ex);
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(ApiResponse.withError(ex.getMessage(), String.valueOf(HttpStatus.NOT_FOUND.value())));
    }

    /**
     * Handles ProductAlreadyExistsException and returns a 409 Conflict response with an error message.
     *
     * @param ex the ProductAlreadyExistsException instance
     * @return a ResponseEntity containing an ApiResponse with the error details
     */
    @ExceptionHandler(ProductAlreadyExistsException.class)
    public ResponseEntity<ApiResponse<Void>> productAlreadyExistsExceptionHandler(ProductAlreadyExistsException ex) {
        log.error("Duplicate", ex);
        return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .body(ApiResponse.withError(ex.getMessage(), String.valueOf(HttpStatus.CONFLICT.value())));
    }

    /**
     * Handles IllegalArgumentException and returns a 400 Bad Request response with an error message.
     *
     * @param ex the IllegalArgumentException instance
     * @return a ResponseEntity containing an ApiResponse with the error details
     */
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ApiResponse<Void>> illegalArgumentExceptionHandler(IllegalArgumentException ex) {
        log.error("Bad request", ex);
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(ApiResponse.withError(ex.getMessage(), String.valueOf(HttpStatus.BAD_REQUEST.value())));
    }

    /**
     * Handles Exception and returns a 500 Internal Server Error response with an error message.
     *
     * @param ex the Exception instance
     * @return a ResponseEntity containing an ApiResponse with the error details
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse<Void>> globalExceptionHandler(Exception ex) {
        log.error("Unexpected exception", ex);
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(ApiResponse.withError(ex.getMessage(), String.valueOf(HttpStatus.INTERNAL_SERVER_ERROR.value())));
    }

    /**
     * Handles Error and returns a 500 Internal Server Error response with an error message.
     *
     * @param e the Error instance
     * @return a ResponseEntity containing an ApiResponse with the error details
     */
    @ExceptionHandler(Error.class)
    public ResponseEntity<ApiResponse<Void>> globalErrorHandler(Error e) {
        log.error("Unexpected error", e);
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(ApiResponse.withError(e.getMessage(), String.valueOf(HttpStatus.INTERNAL_SERVER_ERROR.value())));
    }
}
