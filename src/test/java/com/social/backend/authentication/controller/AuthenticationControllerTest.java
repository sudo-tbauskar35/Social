package com.social.backend.authentication.controller;

import com.social.backend.authentication.exception.InvalidUserException;
import com.social.backend.model.User;
import com.social.backend.model.api.ApiResponse;
import com.social.backend.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.TestPropertySource;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

/**
 * Test class for {@link AuthenticationController}
 */
@TestPropertySource("classpath:application-test.properties")
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class AuthenticationControllerTest {

    private static final String LOCAL_HOST_URI_TEMPLATE = "http://localhost:%s/";

    private static final String REGISTER_API_URI = "/api/auth/register";
    private static final String LOGIN_API_URI = "/api/auth/login";

    private static final String FIRST_NAME = "John";
    private static final String LAST_NAME = "Doe";
    private static final String USERNAME = "johndoe";
    private static final String EMAIL = "johndoe@email.com";
    private static final String PASSWORD = "1234";
    private static final String INVALID_PASSWORD = "2345";

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private TestRestTemplate restTemplate;

    @LocalServerPort
    private Integer port;

    @Test
    public void contextLoads() {
        assertThat(userRepository).isNotNull();
        assertThat(restTemplate).isNotNull();
        assertThat(port).isNotNull();
    }

    @Test
    public void testRegistration() {
        ApiResponse response = restTemplate.exchange(String.format(LOCAL_HOST_URI_TEMPLATE + REGISTER_API_URI, port),
                        HttpMethod.POST,
                        new HttpEntity<User>(new User(FIRST_NAME, LAST_NAME, USERNAME, EMAIL, PASSWORD)),
                        ApiResponse.class)
                .getBody();

        assertNotNull(response);
        assertEquals(response.getStatus(), HttpStatus.ACCEPTED);
        assertNotNull(response.getMessage());
    }

    @Test
    public void testRegistrationWhenAccountAlreadyExists() {
        User user = new User(FIRST_NAME, LAST_NAME, USERNAME, EMAIL, PASSWORD);
        userRepository.save(user);

        ApiResponse response = restTemplate.exchange(String.format(LOCAL_HOST_URI_TEMPLATE + REGISTER_API_URI, port),
                        HttpMethod.POST,
                        new HttpEntity<User>(new User(FIRST_NAME, LAST_NAME, USERNAME, EMAIL, PASSWORD)),
                        ApiResponse.class)
                .getBody();

        assertNotNull(response);
        assertEquals(response.getStatus(), HttpStatus.BAD_REQUEST);
        assertNotNull(response.getErrors());
        assertNotNull(response.getErrors().getFirst());
    }

    @Test
    public void testLoginWithNonExistingEmail() {
        ApiResponse response = restTemplate.exchange(String.format(LOCAL_HOST_URI_TEMPLATE + LOGIN_API_URI, port),
                        HttpMethod.POST,
                        new HttpEntity<>(new User(null, null, null, EMAIL, PASSWORD)),
                        ApiResponse.class)
                .getBody();

        assertNotNull(response);
        assertEquals(response.getStatus(), HttpStatus.BAD_REQUEST);
        assertNotNull(response.getErrors());
        assertEquals(response.getErrors().getFirst(), InvalidUserException.USER_NOT_FOUND_MESSAGE);
    }

    @Test
    public void testLoginWithInvalidPassword() {
        User user = new User(FIRST_NAME, LAST_NAME, USERNAME, EMAIL, PASSWORD);
        userRepository.save(user);

        ApiResponse response = restTemplate.exchange(String.format(LOCAL_HOST_URI_TEMPLATE + LOGIN_API_URI, port),
                        HttpMethod.POST,
                        new HttpEntity<>(new User(null, null, null, EMAIL, INVALID_PASSWORD)),
                        ApiResponse.class)
                .getBody();

        assertNotNull(response);
        assertEquals(response.getStatus(), HttpStatus.BAD_REQUEST);
        assertNotNull(response.getErrors());
        assertEquals(response.getErrors().getFirst(), InvalidUserException.INVALID_CREDENTIALS_MESSAGE);
    }

    @Test
    public void testLoginWithCorrectCredentials() {
        User user = new User(FIRST_NAME, LAST_NAME, USERNAME, EMAIL, PASSWORD);
        userRepository.save(user);

        ApiResponse response = restTemplate.exchange(String.format(LOCAL_HOST_URI_TEMPLATE + LOGIN_API_URI, port),
                        HttpMethod.POST,
                        new HttpEntity<>(new User(null, null, null, EMAIL, PASSWORD)),
                        ApiResponse.class)
                .getBody();

        assertNotNull(response);
        assertEquals(response.getStatus(), HttpStatus.ACCEPTED);
        assertNotNull(response.getMessage());
        assertNotNull(response.getToken());
    }
}
