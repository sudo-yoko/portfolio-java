package com.example;

public class InvalidSelectionException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    public InvalidSelectionException(String message) {
        super(message);
    }
}