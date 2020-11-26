package org.neonsis.socialnetwork.service.security;

import org.neonsis.socialnetwork.model.domain.user.User;
import org.springframework.security.core.Authentication;

public interface AuthenticationFacade {

    Authentication getAuthentication();

    Long getUserId();

    User getLoggedInUser();
}
