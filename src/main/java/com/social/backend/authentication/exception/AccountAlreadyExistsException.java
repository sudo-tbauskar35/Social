package com.social.backend.authentication.exception;

import com.social.backend.exception.BaseException;
import org.springframework.http.HttpStatus;

/**
 * When thrown indicates that a user account already exists with given details
 */
public class AccountAlreadyExistsException extends BaseException {

    public AccountAlreadyExistsException(String message) {
        super(HttpStatus.BAD_REQUEST, message);
    }
}
