package com.jen.library.error;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

        @ExceptionHandler(ObjectNotFoundException.class)
        public ResponseEntity<String> ObjectNotFoundException(ObjectNotFoundException exception) {

                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                                .body(exception.getMessage());
        }

        @ExceptionHandler(MethodArgumentNotValidException.class)
        public ResponseEntity<String> handleInvalidInputException(MethodArgumentNotValidException exception) {

                StringBuilder errorMessage = new StringBuilder("Validation errors:\n");

                exception.getBindingResult().getFieldErrors().forEach(
                                error -> errorMessage.append(error.getField()).append(": ")
                                                .append(error.getDefaultMessage()).append("\n"));

                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorMessage.toString());
        }

        @ExceptionHandler(Exception.class)
        public ResponseEntity<String> handleExceptions(Exception exception) {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                                .body("There has been an internal server error");
        }

        @ExceptionHandler(DataIntegrityViolationException.class)
        public ResponseEntity<String> handleDataIntegrityViolationException(Exception exception) {
                return ResponseEntity.status(HttpStatus.CONFLICT)
                                .body("The provided data conflicts with existing records");
        }

        @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
        public ResponseEntity<String> handleMethodNotSupportedException(Exception exception) {
                return ResponseEntity.status(HttpStatus.METHOD_NOT_ALLOWED)
                                .body("The Request method is not supported for the api  ");
        }
}
