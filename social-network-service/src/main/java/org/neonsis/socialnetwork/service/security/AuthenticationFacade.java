package org.neonsis.socialnetwork.service.security;

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
}
