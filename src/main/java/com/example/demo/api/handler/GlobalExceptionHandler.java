package com.example.demo.api.handler;

import com.example.demo.api.response.ApiResponse;
import com.example.demo.support.error.HttpException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
@RequiredArgsConstructor
public class GlobalExceptionHandler {
    private final SlackNotifier slackNotifier;

    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResponse.ValidationResponse> handleMethodArgumentNotValidException(MethodArgumentNotValidException e, HttpServletRequest request) {
        Map<String, String> errors = new HashMap<>();

        e.getBindingResult().getAllErrors().forEach((error) -> {
            errors.put(((FieldError) error).getField(), error.getDefaultMessage());
        });

        return ApiResponse.error(HttpStatus.UNPROCESSABLE_ENTITY, request.getRequestURI(), "Validation Error", errors);
    }

    @ExceptionHandler(value = HttpException.class)
    public ResponseEntity<ApiResponse.ErrorResponse> handleHttpException(HttpException e, HttpServletRequest request) {
        return ApiResponse.error(e.getHttpStatus(), request.getRequestURI(), e.getMessage());
    }

    @ExceptionHandler(value = Exception.class)
    public ResponseEntity<ApiResponse.ErrorResponse> handleException(Exception e, HttpServletRequest request) {
        slackNotifier.sendNotification(request, e);

        return ApiResponse.error(HttpStatus.INTERNAL_SERVER_ERROR, request.getRequestURI(), "Server Error", e.getMessage());
    }
}
