package com.joaocarlos.email_service.exceptions;

public class InvalidEmailStrategyException extends RuntimeException {
    public InvalidEmailStrategyException(String message) {
        super(message);
    }
}
