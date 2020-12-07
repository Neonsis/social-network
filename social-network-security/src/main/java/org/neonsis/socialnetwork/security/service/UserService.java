package org.neonsis.socialnetwork.security.service.;

import org.neonsis.socialnetwork.security.exception.EmailAlreadyExistsException;
import org.neonsis.socialnetwork.security.model.domain.User;
import org.neonsis.socialnetwork.security.model.dto.RegistrationDto;
import org.neonsis.socialnetwork.security.model.dto.UserDto;

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
     * @throws EmailAlreadyExistsException if user with {@link User#email} already exists.
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
