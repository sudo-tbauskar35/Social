package com.social.backend.hello.controller;

import com.social.backend.hello.service.HelloService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Test class for {@link HelloController}
 */
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class HelloControllerTest {

    private static final String LOCAL_HOST_URI_TEMPLATE = "http://localhost:%s/";

    private static final String HELLO_API_URI = "/api/hello";

    @Autowired
    private HelloController helloController;
    @Autowired
    private TestRestTemplate restTemplate;

    @LocalServerPort
    private Integer port;

    @Test
    public void contextLoads() {
        assertThat(helloController).isNotNull();
        assertThat(restTemplate).isNotNull();

        assertThat(port).isNotNull();
    }

    @Test
    public void testHello() {
        String response = restTemplate.getForObject(String.format(LOCAL_HOST_URI_TEMPLATE + HELLO_API_URI, port), String.class);
        assertEquals(response, HelloService.WELCOME_MESSAGE);
    }
}
