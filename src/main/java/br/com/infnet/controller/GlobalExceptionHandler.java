package br.com.infnet.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleGeneral(Exception ex) {
        return ResponseEntity.status(500)
                .body("Erro inesperado. Por favor, tente novamente.");
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<String> handleInvalid(IllegalArgumentException ex) {
        return ResponseEntity.status(400)
                .body("Entrada inválida: " + ex.getMessage());
    }
}
