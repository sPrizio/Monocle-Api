package com.priago.monocleapi.core.repositories.user;

import com.priago.monocleapi.core.models.entities.user.User;
import com.priago.monocleapi.core.repositories.MonocleRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * DAO access-layer for {@link User}
 *
 * @author Stephen Prizio <a href="http://www.saprizio.com">www.saprizio.com</a>
 * @version 1.0
 */
@Repository
public interface UserRepository extends MonocleRepository, UserRepositoryCustom, PagingAndSortingRepository<User, Long> {

    /**
     * Attempts to find a {@link User} for the given email
     *
     * @param email email to look up
     * @return {@link Optional} {@link User}
     */
    Optional<User> findUserByEmail(String email);
}
