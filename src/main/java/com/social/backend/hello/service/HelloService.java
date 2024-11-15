package com.social.backend.hello.service;

import org.springframework.stereotype.Component;

/**
 * Basic service
 */
@Component
public class HelloService {

    public static final String WELCOME_MESSAGE = "Hello. Welcome to social!";

    public String hello() {
        return WELCOME_MESSAGE;
    }
}
