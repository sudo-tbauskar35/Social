package com.social.backend.exception;

import org.springframework.http.HttpStatus;

/**
 * Base exception class used by all exceptions thrown in this application
 */
public class BaseException extends Exception {

    private final HttpStatus status;

    public BaseException(HttpStatus status, String message) {
        super(message);
        this.status = status;
    }

    public HttpStatus getStatus() {
        return status;
    }
}
