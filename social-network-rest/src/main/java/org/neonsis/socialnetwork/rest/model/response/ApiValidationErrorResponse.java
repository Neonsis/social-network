package org.neonsis.socialnetwork.rest.model.response;

import lombok.Getter;
import lombok.Setter;
import org.neonsis.socialnetwork.rest.exception.model.ValidationError;
import org.springframework.http.HttpStatus;

import java.util.List;

@Getter
@Setter
public class ApiValidationErrorResponse extends ApiErrorResponse {

    private List<ValidationError> details;

    public ApiValidationErrorResponse(HttpStatus status) {
        super(status);
    }

    public ApiValidationErrorResponse(HttpStatus status, String message, List<ValidationError> details) {
        super(status, message);
        this.details = details;
    }
}
