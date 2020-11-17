package org.neonsis.socialnetwork.exception;

/**
 * Abstract exception for business logic, don't print stack trace
 *
 * @author neonsis
 */
public abstract class BusinessException extends ApplicationException {

    /**
     * Create a {@link BusinessException}.
     *
     * @param message the detail message.
     */
    public BusinessException(String message) {
        super(message, null, true, false);
    }
}