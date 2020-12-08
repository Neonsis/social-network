package org.neonsis.socialnetwork.security.exception;

public class UserNotFoundException extends SecurityException {

    /**
     * Create a {@link UserNotFoundException}
     *
     * @param message the detail message.
     */
    public UserNotFoundException(String message) {
        super(message);
    }
}
