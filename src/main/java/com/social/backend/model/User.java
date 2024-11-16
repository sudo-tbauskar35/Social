package com.social.backend.model;

import jakarta.persistence.*;

import java.time.ZonedDateTime;

/**
 * Data model class for User
 */
@Entity
@NamedQueries({
        @NamedQuery(
                name = User.FIND_USER_BY_EMAIL,
                query = "select u from User u where u.email = :email"
        )
})
@Table(name = User.TABLE_NAME)
public class User {

    public static final String TABLE_NAME = "USER_ACCOUNT";

    public static final String FIND_USER_BY_EMAIL = "findUserByEmail";

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    private String firstName;
    private String lastName;
    private String username;
    private String email;
    private String password;

    private ZonedDateTime createdOn;

    // For JPA
    public User() {
    }

    public User(String firstName, String lastName, String username, String email, String password) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.username = username;
        this.email = email;
        this.password = password;
        this.createdOn = ZonedDateTime.now();
    }

    public Long getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public ZonedDateTime getCreatedOn() {
        return createdOn;
    }
}
