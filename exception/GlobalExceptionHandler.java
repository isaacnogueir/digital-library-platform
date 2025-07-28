package com.project2025.auth_2.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<?> handlerBusinessException(BusinessException ex) {
        return ResponseEntity.badRequest().body(
                new ErrorResponse(ex.getMessage(), ex.getCode().name())
        );
    }
        static class ErrorResponse {
            public String message;
            public String code;

            public ErrorResponse(String message, String code) {
                this.message = message;
                this.code = code;
            }
        }
    }
