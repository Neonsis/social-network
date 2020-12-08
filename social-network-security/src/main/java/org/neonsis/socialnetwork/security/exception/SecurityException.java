package org.neonsis.socialnetwork.security.exception;

/**
 * Abstract exception for all exceptions in security modules, doesn't print stack trace
 *
 * @author neonsis
 */
public abstract class SecurityException extends RuntimeException {

    /**
     * Create a {@link SecurityException}.
     *
     * @param message the detail message.
     */
    public SecurityException(String message) {
        super(message, null, true, false);
    }
}
