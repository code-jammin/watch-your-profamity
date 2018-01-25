package com.creatingbugs.controller;

/**
 * Created by steve on 24/01/18.
 */
public class CustomConflictException extends RuntimeException {
    public CustomConflictException() {
        super();
    }

    public CustomConflictException(String message) {
        super(message);
    }

    public CustomConflictException(String message, Throwable cause) {
        super(message, cause);
    }

    public CustomConflictException(Throwable cause) {
        super(cause);
    }
}
