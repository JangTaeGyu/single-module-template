package com.example.demo.support.error;

import lombok.Getter;

@Getter
public class DomainException extends HttpException {
    public DomainException(String message) {
        super(message);
    }
}
