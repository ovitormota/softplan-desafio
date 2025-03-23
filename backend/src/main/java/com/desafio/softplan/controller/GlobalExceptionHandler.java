package com.desafio.softplan.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.server.ResponseStatusException;

import com.desafio.softplan.domain.dto.ErroResponseDTO;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ResponseStatusException.class)
    public ResponseEntity<ErroResponseDTO> handleResponseStatusException(ResponseStatusException ex) {
        ErroResponseDTO erro = new ErroResponseDTO(
                ex.getStatusCode().value(),
                ex.getReason() != null ? ex.getReason() : "Erro desconhecido");
        return new ResponseEntity<>(erro, ex.getStatusCode());
    }
}
