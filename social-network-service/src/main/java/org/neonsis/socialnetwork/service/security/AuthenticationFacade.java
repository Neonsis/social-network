package org.neonsis.socialnetwork.service.security;

import org.neonsis.socialnetwork.model.domain.user.User;
import org.springframework.security.core.Authentication;

/**
 * AuthenticationFacade.
 *
 * @author neonsis
 */
public interface AuthenticationFacade {

    /**
     * Get the authentication information.
     *
     * @return {@code Authentication} object.
     */
    Authentication getAuthentication();

    /**
     * Get the logged in user id.
     *
     * @return the user id.
     */
    Long getLoggedInUserId();

    /**
     * Get the logged in user.
     *
     * @return the user.
     */
    User getLoggedInUser();
}
