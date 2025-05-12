package com.bach.task_flow.configs;

import com.bach.task_flow.dtos.ApiResponse;
import com.bach.task_flow.exceptions.ErrorCode;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.io.IOException;
import java.util.Objects;

@Component
public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        ErrorCode errorCode = ErrorCode.UNAUTHENTICATED;

        response.setStatus(errorCode.getHttpStatusCode().value());
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);

        ApiResponse<?> apiResponse = ApiResponse.builder()
                .code(errorCode.getCode())
                .message(errorCode.getMessage())
                .build();

        ObjectMapper objectMapper = new ObjectMapper();

        response.getWriter().write(objectMapper.writeValueAsString(apiResponse));
        response.flushBuffer();
    }

    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResponse<String>> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        ApiResponse<String> response = new ApiResponse<>();
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
                .status(errorCode.getHttpStatusCode())
                .body(response);
    }

}
