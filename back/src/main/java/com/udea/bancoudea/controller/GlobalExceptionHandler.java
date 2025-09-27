package com.udea.bancoudea.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.HashMap;
import java.util.Map;

/**
 * Manejador global de excepciones para respuestas consistentes de error
 */
@ControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    /**
     * Maneja errores de validación y reglas de negocio
     */
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<Map<String, String>> handleIllegalArgumentException(IllegalArgumentException ex) {
        logger.error("Error de validación: {}", ex.getMessage());
        
        Map<String, String> errorResponse = new HashMap<>();
        errorResponse.put("error", ex.getMessage());
        
        if (ex.getMessage().contains("not found")) {
            errorResponse.put("code", "ACCOUNT_NOT_FOUND");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
        } else if (ex.getMessage().contains("Insufficient funds")) {
            errorResponse.put("code", "INSUFFICIENT_FUNDS");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
        } else if (ex.getMessage().contains("cannot be the same")) {
            errorResponse.put("code", "SAME_ACCOUNT_ERROR");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
        } else if (ex.getMessage().contains("cannot be null") || ex.getMessage().contains("must be greater than 0")) {
            errorResponse.put("code", "VALIDATION_ERROR");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
        } else {
            errorResponse.put("code", "VALIDATION_ERROR");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
        }
    }

    /**
     * Maneja errores inesperados del sistema
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, String>> handleGenericException(Exception ex) {
        logger.error("Error inesperado: {}", ex.getMessage(), ex);
        
        Map<String, String> errorResponse = new HashMap<>();
        errorResponse.put("error", "Ocurrió un error inesperado");
        errorResponse.put("code", "INTERNAL_SERVER_ERROR");
        
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
    }
}
