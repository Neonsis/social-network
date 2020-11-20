package org.neonsis.socialnetwork.exception;

import org.neonsis.socialnetwork.model.domain.base.AbstractBaseEntity;

/**
 * Throws when <em>"{@link AbstractBaseEntity} not found"</em>
 *
 * @author neonsis
 */
public class EntityNotFoundException extends BusinessException {

    /**
     * Create a {@link EntityNotFoundException}.
     *
     * @param message the detail message.
     */
    public EntityNotFoundException(String message) {
        super(message);
    }
}