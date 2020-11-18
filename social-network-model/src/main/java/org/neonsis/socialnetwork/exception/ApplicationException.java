package org.neonsis.socialnetwork.exception;

/**
 * Abstract exception for all custom exceptions
 *
 * @author neonsis
 */
public abstract class ApplicationException extends RuntimeException {

    /**
     * Create a {@link ApplicationException}.
     *
     * @param message the detail message.
     */
    public ApplicationException(String message) {
        super(message);
    }

    /**
     * Create a {@link ApplicationException}.
     *
     * @param message the detail message.
     * @param cause   the cause. (A {@code null} value is permitted, and indicates that the cause is
     *                nonexistent or unknown.)
     */
    public ApplicationException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * Create a {@link ApplicationException}.
     *
     * @param cause the cause. (A {@code null} value is permitted, and indicates that the cause is
     *              nonexistent or unknown.)
     */
    public ApplicationException(Throwable cause) {
        super(cause);
    }

    /**
     * Create a {@link ApplicationException}.
     *
     * @param message            the detail message.
     * @param cause              the cause. (A {@code null} value is permitted, and indicates that the cause is
     *                           nonexistent or unknown.)
     * @param enableSuppression  whether or not suppression is enabled or disabled
     * @param writableStackTrace whether or not the stack trace should be writable
     */
    public ApplicationException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}