package org.neonsis.socialnetwork.security.facade;

import lombok.RequiredArgsConstructor;
import org.neonsis.socialnetwork.security.model.domain.User;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

/**
 * AuthenticationFacade.
 *
 * @author neonsis
 */
@Component
@RequiredArgsConstructor
public class AuthenticationFacadeImpl implements AuthenticationFacade {

    @Override
    public Authentication getAuthentication() {
        return SecurityContextHolder.getContext().getAuthentication();
    }

    @Override
    public Long getLoggedInUserId() {
        return ((User) getAuthentication().getPrincipal()).getId();
    }
}
