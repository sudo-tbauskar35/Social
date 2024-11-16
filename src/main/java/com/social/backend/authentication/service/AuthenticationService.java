package com.social.backend.authentication.service;

import com.social.backend.authentication.exception.AccountAlreadyExistsException;
import com.social.backend.authentication.exception.InvalidUserException;
import com.social.backend.model.User;
import com.social.backend.repository.UserRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class AuthenticationService {

    private static final Logger LOGGER = LoggerFactory.getLogger(AuthenticationService.class);

    private static final String ACCOUNT_ALREADY_EXISTS = "Account with this email already exists. Login instead?";

    @Autowired
    private UserRepository userRepository;

    @PersistenceContext
    private EntityManager entityManager;

    public void register(User user) throws AccountAlreadyExistsException {
        LOGGER.info("Creating account for user: {} {}", user.getFirstName(), user.getLastName());

        TypedQuery<User> query = entityManager.createNamedQuery(User.FIND_USER_BY_EMAIL, User.class);
        query.setParameter("email", user.getEmail());
        Optional<User> existingUser = query.getResultList().stream().findFirst();

        if (existingUser.isPresent()) {
            throw new AccountAlreadyExistsException(ACCOUNT_ALREADY_EXISTS);
        }

        userRepository.save(user);
    }

    public User login(User user) throws InvalidUserException {
        TypedQuery<User> query = entityManager.createNamedQuery(User.FIND_USER_BY_EMAIL, User.class);
        query.setParameter("email", user.getEmail());
        Optional<User> existingUser = query.getResultList().stream().findFirst();

        if (existingUser.isPresent()) {
            if (existingUser.get().getPassword().equals(user.getPassword())) {
                return existingUser.get();
            }
            throw new InvalidUserException(InvalidUserException.INVALID_CREDENTIALS_MESSAGE);
        }

        throw new InvalidUserException(InvalidUserException.USER_NOT_FOUND_MESSAGE);
    }
}
