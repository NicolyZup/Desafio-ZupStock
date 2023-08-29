package com.zup.stock.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class HandlerException {
    @ExceptionHandler(IdNaoLocalizadoException.class)
    public ResponseEntity<String> handleIdNaoLocalizadoException(IdNaoLocalizadoException e){
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
    }

    @ExceptionHandler(ParametroInvalidoException.class)
    public ResponseEntity<String> handleParametroInvalidoException(ParametroInvalidoException e){
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Parâmetro inválido.");
    }
}
