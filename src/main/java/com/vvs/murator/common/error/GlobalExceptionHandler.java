package com.vvs.murator.common.error;

import com.vvs.murator.user.exception.ExistLoginIdException;
import com.vvs.murator.user.exception.LoginFailException;
import com.vvs.murator.user.exception.UserNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    // COMMON
    @ExceptionHandler(MethodArgumentNotValidException.class)
    ResponseEntity<ErrorResponse> methodArgumentNotValidException(MethodArgumentNotValidException e) {
        FieldError error = e.getBindingResult().getFieldErrors().get(0);

        return getErrorResponseEntity(e,
                ErrorCode.METHOD_ARGUMENT_NOT_VALID,
                error.getDefaultMessage(),
                error.getField());
    }

    // USER
    @ExceptionHandler(ExistLoginIdException.class)
    ResponseEntity<ErrorResponse> existLoginIdException(ExistLoginIdException e) {
        return getErrorResponseEntity(e, e.getErrorCode());
    }

    @ExceptionHandler(LoginFailException.class)
    ResponseEntity<ErrorResponse> loginFailException(LoginFailException e) {
        return getErrorResponseEntity(e, e.getErrorCode());
    }

    @ExceptionHandler(UserNotFoundException.class)
    ResponseEntity<ErrorResponse> userNotFoundException(UserNotFoundException e) {
        return getErrorResponseEntity(e, e.getErrorCode());
    }

    private static ResponseEntity<ErrorResponse> getErrorResponseEntity(Exception e, ErrorCode errorCode) {
        log.error("GlobalException = {} {}", e.getCause().getMessage(), e.getMessage());

        final ErrorResponse res = ErrorResponse.of(errorCode);
        return new ResponseEntity<>(res, HttpStatus.valueOf(errorCode.getStatus()));
    }

    private static ResponseEntity<ErrorResponse> getErrorResponseEntity(Exception e, ErrorCode errorCode, String message, String field) {
        log.error("GlobalException = {}", e.getMessage());

        final ErrorResponse res = ErrorResponse.of(errorCode, message, field);
        return new ResponseEntity<>(res, HttpStatus.valueOf(errorCode.getStatus()));
    }
}
