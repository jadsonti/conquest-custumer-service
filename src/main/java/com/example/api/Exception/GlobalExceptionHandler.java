package com.example.api.Exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Object> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();

        ex.getBindingResult().getFieldErrors().forEach(error -> {
            String fieldName = error.getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, processErrorMessage(fieldName, errorMessage));
        });

        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
    }
    private String processErrorMessage(String fieldName, String errorMessage) {
        if (fieldName.equals("email") && errorMessage.equals("must not be blank")) {
            return "O campo de e-mail não pode estar em branco.";
        } else if (fieldName.equals("name") && errorMessage.equals("must not be blank")) {
            return "O nome deve ter pelo menos 2 caracteres.";
        } else if (fieldName.equals("gender") && errorMessage.equals("must not be blank")) {
            return "O campo de gênero não pode estar em branco.";
        }
        return errorMessage;
    }
}



