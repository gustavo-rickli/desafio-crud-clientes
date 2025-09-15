package com.gustavorickli.crud_clientes.controller.handlers;

import com.gustavorickli.crud_clientes.dto.CustomError;
import com.gustavorickli.crud_clientes.dto.ValidationError;
import com.gustavorickli.crud_clientes.service.exceptions.ResourceNotFoundException;
import jakarta.persistence.EntityNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.Instant;

@ControllerAdvice
public class ControllerExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<CustomError> resourceNotFound(ResourceNotFoundException e, HttpServletRequest request) {
        CustomError err = new CustomError(Instant.now(), HttpStatus.NOT_FOUND.value(), e.getMessage(), request.getRequestURI());

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(err);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<CustomError> methodArgumentNotValid(MethodArgumentNotValidException e, HttpServletRequest request) {
        ValidationError err = new ValidationError(
                Instant.now(), HttpStatus.UNPROCESSABLE_ENTITY.value(), "dados invalidos", request.getRequestURI()
        );

        // err.addError("name", "mensagem de teste");
        // err.addError("price", "preco invalido");

        // mapear os erros da exception para dentro da lista de ValidationError
        for (FieldError fieldError : e.getBindingResult().getFieldErrors()) {
            err.addError(fieldError.getField(), fieldError.getDefaultMessage());
        }

        return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(err);
    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResponseEntity<CustomError> httpRequestMethodNotSupported(HttpRequestMethodNotSupportedException e, HttpServletRequest request) {
        CustomError err = new CustomError(Instant.now(), HttpStatus.BAD_REQUEST.value(), String.format("recurso: %s, method: %s nao encontrado", request.getRequestURI(), request.getMethod()), request.getRequestURI());

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(err);
    }

}
