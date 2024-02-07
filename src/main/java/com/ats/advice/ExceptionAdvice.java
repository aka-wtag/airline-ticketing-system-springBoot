package com.ats.advice;


import com.ats.exception.*;
import com.ats.message.ErrorMessage;
import io.jsonwebtoken.ExpiredJwtException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class ExceptionAdvice {
    private final ErrorMessage errorMessage;

    @Autowired
    public ExceptionAdvice(ErrorMessage errorMessage) {
        this.errorMessage = errorMessage;
    }

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<ErrorMessage> handleBadClientRequest(BadRequestException exception){
        errorMessage.setMessage(exception.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorMessage);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Map<String,String> handleException(MethodArgumentNotValidException exception){
        Map<String,String> errorMap = new HashMap<>();
        exception.getBindingResult().getFieldErrors().forEach(error -> errorMap.put(error.getField(),error.getDefaultMessage()));
        return errorMap;
    }

    @ExceptionHandler(ObjectNotFoundException.class)
    public ResponseEntity<ErrorMessage> handleObjectNotFoundException(ObjectNotFoundException exception){
        errorMessage.setMessage(exception.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorMessage);
    }

    @ExceptionHandler(UserAlreadyExistsException.class)
    public ResponseEntity<ErrorMessage> handleUserAlreadyExistsException(UserAlreadyExistsException exception){
        errorMessage.setMessage(exception.getMessage());
        return ResponseEntity.status(HttpStatus.CONFLICT).body(errorMessage);
    }

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<ErrorMessage> exceptionHandler(BadCredentialsException exception) {
        errorMessage.setMessage(exception.getMessage());
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(errorMessage);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ErrorMessage> httpMessageNotReadableExceptionHandler() {
        errorMessage.setMessage("Error in input. Cannot process entity");
        return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(errorMessage);
    }
}
