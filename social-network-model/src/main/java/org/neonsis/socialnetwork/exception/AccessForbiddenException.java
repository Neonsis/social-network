package org.neonsis.socialnetwork.exception;

/**
 * Throws when user doesn't have permission to resource
 *
 * @author neonsis
 */
public class AccessForbiddenException extends BusinessException {

    /**
     * Create a {@link AccessForbiddenException}.
     *
     * @param message the detail message.
     */
    public AccessForbiddenException(String message) {
        super(message);
    }
}