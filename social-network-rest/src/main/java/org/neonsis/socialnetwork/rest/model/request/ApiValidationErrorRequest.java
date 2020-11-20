package org.neonsis.socialnetwork.rest.model.request;

import lombok.Getter;
import lombok.Setter;
import org.neonsis.socialnetwork.rest.exception.model.ValidationError;
import org.springframework.http.HttpStatus;

import java.util.List;

@Getter
@Setter
public class ApiValidationErrorRequest extends ApiErrorRequest {

    private List<ValidationError> details;

    public ApiValidationErrorRequest(HttpStatus status) {
        super(status);
    }

    public ApiValidationErrorRequest(HttpStatus status, String message, List<ValidationError> details) {
        super(status, message);
        this.details = details;
    }
}
