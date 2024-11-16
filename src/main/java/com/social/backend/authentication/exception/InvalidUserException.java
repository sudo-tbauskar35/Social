package com.social.backend.authentication.exception;

import com.social.backend.exception.BaseException;
import org.springframework.http.HttpStatus;

/**
 * When thrown, indicates that user with the given credentials does not exist
 */
public class InvalidUserException extends BaseException {

    public static final String USER_NOT_FOUND_MESSAGE = "Account for this email does not exist";
    public static final String INVALID_CREDENTIALS_MESSAGE = "Invalid password. Please try again";

    public InvalidUserException(String message) {
        super(HttpStatus.BAD_REQUEST, message);
    }
}
