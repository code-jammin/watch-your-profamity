package com.creatingbugs.service;

/**
 * An exception class to be used when a word already exists on the database.
 *
 * Created by steve on 20/01/18.
 */
public class WordAlreadyExistsException extends Exception {
    public WordAlreadyExistsException() {
        super();
    }

    public WordAlreadyExistsException(String message) {
        super(message);
    }

    public WordAlreadyExistsException(String message, Throwable cause) {
        super(message, cause);
    }

    public WordAlreadyExistsException(Throwable cause) {
        super(cause);
    }
}
