package com.example.demo.api.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Date;
import java.util.Map;

public class ApiResponse {
    public record SuccessfulResponse<T>(T data) {
    }

    @Getter
    public static class ErrorResponse {
        @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
        private final Date timestamp = new Date();
        private final Integer status;
        private final String message;
        private final String path;

        @JsonIgnore
        private final String details;

        public ErrorResponse(Integer status, String path, String message) {
            this.status = status;
            this.path = path;
            this.message = message;
            this.details = null;
        }

        public ErrorResponse(Integer status, String path, String message, String details) {
            this.status = status;
            this.path = path;
            this.message = message;
            this.details = details;
        }
    }

    @Getter
    public static class ValidationResponse extends ErrorResponse {
        private final Map<String, String> errors;

        public ValidationResponse(Integer status, String path, String code, Map<String, String> errors) {
            super(status, path, code);
            this.errors = errors;
        }
    }

    public static <T> ResponseEntity<SuccessfulResponse<T>> successful(@NotNull T body) {
        SuccessfulResponse<T> successfulResponse = new SuccessfulResponse<>(body);

        return ResponseEntity.ok(successfulResponse);
    }

    public static ResponseEntity<Void> successful(HttpStatus httpStatus) {
        return new ResponseEntity<>(null, httpStatus);
    }

    public static ResponseEntity<ErrorResponse> error(HttpStatus httpStatus, String path, String message) {
        ErrorResponse errorResponse = new ErrorResponse(httpStatus.value(), path, message);

        return new ResponseEntity<>(errorResponse, httpStatus);
    }

    public static ResponseEntity<ErrorResponse> error(HttpStatus httpStatus, String path, String message, String details) {
        ErrorResponse errorResponse = new ErrorResponse(httpStatus.value(), path, message, details);

        return new ResponseEntity<>(errorResponse, httpStatus);
    }

    public static ResponseEntity<ValidationResponse> error(HttpStatus httpStatus, String path, String code, Map<String, String> errors) {
        ValidationResponse errorResponse = new ValidationResponse(httpStatus.value(), path, code, errors);

        return new ResponseEntity<>(errorResponse, httpStatus);
    }
}
