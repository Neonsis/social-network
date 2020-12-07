package org.neonsis.socialnetwork.service;

import org.neonsis.socialnetwork.exception.ValidationException;
import org.neonsis.socialnetwork.model.domain.user.User;
import org.neonsis.socialnetwork.model.dto.user.RegistrationDto;
import org.neonsis.socialnetwork.model.dto.user.UserDto;

/**
 * {@link User} service interface.
 *
 * @author neonsis
 */
public interface UserService {

    /**
     * Register a new user.
     *
     * @param registrationDto a registration object describing the user to register.
     * @return the registered user.
     * @throws ValidationException if user with {@link User#email} already exists.
     */
    UserDto signUp(RegistrationDto registrationDto);

    /**
     * Find a user by id.
     *
     * @param id the id of the searched user.
     * @return the founded user,
     */
    UserDto findById(Long id);

    /**
     * Find a user by email.
     *
     * @param email the email of the searched user.
     * @return the founded user.
     */
    UserDto findByEmail(String email);
}
