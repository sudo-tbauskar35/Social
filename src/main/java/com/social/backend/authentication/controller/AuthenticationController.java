package com.social.backend.authentication.controller;

import com.social.backend.authentication.service.AuthenticationService;
import com.social.backend.authentication.exception.AccountAlreadyExistsException;
import com.social.backend.authentication.exception.InvalidUserException;
import com.social.backend.model.User;
import com.social.backend.model.api.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Controller for authentication related API
 */
@RestController
@RequestMapping("/api/auth")
public class AuthenticationController {

    @Autowired
    private AuthenticationService authenticationService;

    @PostMapping("/register")
    public ApiResponse register(@RequestBody User user) {
        try {
            authenticationService.register(user);
            return ApiResponse.builder()
                    .status(HttpStatus.ACCEPTED)
                    .message("Account created. You can now login")
                    .build();
        } catch (AccountAlreadyExistsException e) {
            return ApiResponse.builder()
                    .status(e.getStatus())
                    .errors(List.of(e.getMessage()))
                    .build();
        }
    }

    @PostMapping("/login")
    public ApiResponse login(@RequestBody User user) {
        try {
            User account = authenticationService.login(user);
            return ApiResponse.builder()
                    .status(HttpStatus.ACCEPTED)
                    .message("Login successful")
                    .token(account.getEmail())
                    .build();
        } catch (InvalidUserException e) {
            return ApiResponse.builder()
                    .status(e.getStatus())
                    .errors(List.of(e.getMessage()))
                    .build();
        }
    }
}
