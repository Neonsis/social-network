package org.neonsis.socialnetwork.exception;

/**
 * Throws when something went wrong due to the user
 *
 * @author neonsis
 */
public class InvalidWorkFlowException extends BusinessException {

    /**
     * Create a {@link InvalidWorkFlowException}.
     *
     * @param message the detail message.
     */
    public InvalidWorkFlowException(String message) {
        super(message);
    }
}