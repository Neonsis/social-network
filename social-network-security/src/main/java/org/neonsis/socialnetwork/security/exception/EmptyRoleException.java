package org.neonsis.socialnetwork.security.exception;

/**
 * Throws when default role not saved in the database.
 */
public class EmptyRoleException extends SecurityException {

    /**
     * Create a {@link EmptyRoleException}.
     *
     * @param message the detail message.
     */
    public EmptyRoleException(String message) {
        super(message);
    }
}
