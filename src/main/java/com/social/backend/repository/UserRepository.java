package com.social.backend.repository;

import com.social.backend.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * DB Repository for {@link User}
 */
public interface UserRepository extends JpaRepository<User, Long> {
}
