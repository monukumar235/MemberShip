package com.firstClub.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(BadRequest.class)
    public ResponseEntity<Map<String,Object>> bad(BadRequest ex){
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse(ex.getMessage(),HttpStatus.BAD_REQUEST));
    }

    @ExceptionHandler(NotFound.class)
    public ResponseEntity<Map<String ,Object>> nf(NotFound ex){
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse(ex.getMessage(),HttpStatus.NOT_FOUND));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String ,Object>> oops(Exception e){
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse(e.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR));
    }

    private Map<String, Object> errorResponse(String message, HttpStatus status) {
        return Map.of(
                "timestamp", LocalDateTime.now().toString(),
                "status", status.value(),
                "error", status.getReasonPhrase(),
                "message", message
        );
    }
}
