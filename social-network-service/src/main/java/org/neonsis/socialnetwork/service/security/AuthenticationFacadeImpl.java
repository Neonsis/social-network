package org.neonsis.socialnetwork.service.security;

import lombok.RequiredArgsConstructor;
import org.neonsis.socialnetwork.model.domain.user.User;
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
        return ((UserPrincipal) getAuthentication().getPrincipal()).getId();
    }
}
