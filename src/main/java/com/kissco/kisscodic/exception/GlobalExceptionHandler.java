package com.kissco.kisscodic.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.validation.ConstraintViolationException;

import static com.kissco.kisscodic.exception.ErrorCode.*;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {


//
    @ExceptionHandler(value = {ConstraintViolationException.class, DataIntegrityViolationException.class
    })
    public ResponseEntity<ErrorResponse> handleDataException(){
        log.error("handleDataException throw Exception : {}", DUPLICATE_RESOURCE);
        return ErrorResponse.toResponseEntity(DUPLICATE_RESOURCE);
    }

    @ExceptionHandler(value = {IllegalArgumentException.class})
    public ResponseEntity<ErrorResponse> handleIllegalArgumentException(){
        log.error("handleIllegalArgumentException throw Exception : {}", MISMATCH_ARGUMENT);
        return ErrorResponse.toResponseEntity(MISMATCH_ARGUMENT);
    }

    @ExceptionHandler(value = {ServletRequestBindingException.class})
    public ResponseEntity<ErrorResponse> handleAuthenticate(){
        log.error("handleDataException throw Exception : {}", INVALID_AUTHENTICATION);
        return ErrorResponse.toResponseEntity(INVALID_AUTHENTICATION);
    }

    @ExceptionHandler(value = { CustomException.class })
    protected ResponseEntity<ErrorResponse> handleCustomException(CustomException e) {
        log.error("handleCustomException throw CustomException : {}", e.getErrorCode());
        return ErrorResponse.toResponseEntity(e.getErrorCode());
    }
}
