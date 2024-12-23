package io.github.arch2be.wwws.framework.api;

import io.github.arch2be.wwws.application.exceptions.WindsurfLocationsAreNotDefinedException;
import io.github.arch2be.wwws.application.exceptions.WrongWindsurfDateException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
class GlobalExceptionHandler {

    @ExceptionHandler(value = WindsurfLocationsAreNotDefinedException.class)
    ResponseEntity<?> handleWindsurfLocationsAreNotDefinedException(WindsurfLocationsAreNotDefinedException ex) {
        return ResponseEntity.badRequest().body(ex.getMessage());
    }

    @ExceptionHandler(value = WrongWindsurfDateException.class)
    ResponseEntity<?> handleWrongWindsurfDateException(WrongWindsurfDateException ex) {
        return ResponseEntity.badRequest().body(ex.getMessage());
    }

    @ExceptionHandler(value = IllegalArgumentException.class)
    ResponseEntity<?> handleIllegalArgumentException(IllegalArgumentException ex) {
        return ResponseEntity.badRequest().body(ex.getMessage());
    }
}