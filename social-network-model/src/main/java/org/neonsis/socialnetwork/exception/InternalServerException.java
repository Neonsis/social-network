package org.neonsis.socialnetwork.exception;

/**
 * Throws when something went wrong on the server
 *
 * @author neonsis
 */
public class InternalServerException extends ApplicationException {

    /**
     * Create a {@link InternalServerException}.
     *
     * @param message the detail message.
     */
    public InternalServerException(String message) {
        super(message);
    }

    /**
     * Create a {@link InternalServerException}.
     *
     * @param message the detail message.
     * @param cause   the cause. (A {@code null} value is permitted, and indicates that the cause is
     *                nonexistent or unknown.)
     */
    public InternalServerException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * Create a {@link InternalServerException}.
     *
     * @param cause the cause. (A {@code null} value is permitted, and indicates that the cause is
     *              nonexistent or unknown.)
     */
    public InternalServerException(Throwable cause) {
        super(cause);
    }

    /**
     * Create a {@link InternalServerException}.
     *
     * @param message            the detail message.
     * @param cause              the cause. (A {@code null} value is permitted, and indicates that the cause is
     *                           nonexistent or unknown.)
     * @param enableSuppression  whether or not suppression is enabled or disabled
     * @param writableStackTrace whether or not the stack trace should be writable
     */
    public InternalServerException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}