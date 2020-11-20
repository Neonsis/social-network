package org.neonsis.socialnetwork.exception;

/**
 * Throws when validation failed.
 *
 * @author neonsis
 */
public class ValidationException extends BusinessException {

    /**
     * Filed that not valid.
     */
    private String field;

    /**
     * Create a {@link BusinessException}.
     *
     * @param message the detail message.
     */
    public ValidationException(String field, String message) {
        super(message);
        this.field = field;
    }
}
