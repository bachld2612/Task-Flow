package com.bach.spring_database.exceptions;

import com.bach.spring_database.dtos.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.Objects;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(value = ApplicationException.class)
    public ResponseEntity<ApiResponse<?>> applicationException(final ApplicationException e) {

        ApiResponse<?> apiResponse = new ApiResponse<>();
        ErrorCode errorCode = e.getErrorCode();
        apiResponse.setCode(errorCode.getCode());
        apiResponse.setMessage(errorCode.getMessage());
        return ResponseEntity
                .status(errorCode.getHttpStatusCode())
                .body(apiResponse);

    }

    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResponse<?>> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        ApiResponse<?> response = new ApiResponse<>();
        ErrorCode errorCode = null;
        try {
            String message = Objects.requireNonNull(e.getFieldError()).getDefaultMessage();
            errorCode = ErrorCode.valueOf(message);
            response.setCode(errorCode.getCode());
            response.setMessage(errorCode.getMessage());
        }catch (Exception ex) {
            errorCode = ErrorCode.INVALID_VALIDATION;
            response.setCode(errorCode.getCode());
            response.setMessage(errorCode.getMessage());
        }
        return ResponseEntity
                .status(errorCode.httpStatusCode)
                .body(response);
    }

}
