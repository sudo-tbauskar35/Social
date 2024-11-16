package com.social.backend.authentication.service;

import com.social.backend.authentication.exception.AccountAlreadyExistsException;
import com.social.backend.model.User;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.TestPropertySource;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@TestPropertySource("classpath:application-test.properties")
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class AuthenticationServiceTest {

    private static final String FIRST_NAME = "John";
    private static final String LAST_NAME = "Doe";
    private static final String USERNAME = "johndoe";
    private static final String EMAIL = "johndoe@email.com";
    private static final String PASSWORD = "1234";

    @Autowired
    private AuthenticationService authenticationService;
    @PersistenceContext
    private EntityManager entityManager;

    @Test
    public void contextLoads() {
        assertThat(authenticationService).isNotNull();
        assertThat(entityManager).isNotNull();
    }

    @Test
    public void testRegister() throws AccountAlreadyExistsException {
        authenticationService.register(new User(FIRST_NAME, LAST_NAME, USERNAME, EMAIL, PASSWORD));

        TypedQuery<User> query = entityManager.createNamedQuery(User.FIND_USER_BY_EMAIL, User.class);
        query.setParameter("email", EMAIL);
        User user = query.getSingleResult();

        assertNotNull(user);

        assertEquals(user.getFirstName(), FIRST_NAME);
        assertEquals(user.getLastName(), LAST_NAME);
        assertEquals(user.getEmail(), EMAIL);
        assertEquals(user.getPassword(), PASSWORD);
    }

    @Test
    public void testRegistrationFailsOnUsingSameEmail() {
        try {
            authenticationService.register(new User(FIRST_NAME, LAST_NAME, USERNAME, EMAIL, PASSWORD));
            authenticationService.register(new User(FIRST_NAME, LAST_NAME, USERNAME, EMAIL, PASSWORD));
        } catch (Exception e) {
            assertInstanceOf(AccountAlreadyExistsException.class, e);
        }
    }
}
