package com.creatingbugs.service;

/**
 * Created by steve on 04/02/18.
 */
public class WordDoesNotExistException extends Exception {
    public WordDoesNotExistException() {
        super();
    }

    public WordDoesNotExistException(String message) {
        super(message);
    }

    public WordDoesNotExistException(String message, Throwable cause) {
        super(message, cause);
    }

    public WordDoesNotExistException(Throwable cause) {
        super(cause);
    }
}
