package org.neonsis.socialnetwork.rest.exception;

import org.neonsis.socialnetwork.exception.RecordNotFoundException;
import org.neonsis.socialnetwork.rest.exception.model.ValidationError;
import org.neonsis.socialnetwork.rest.payload.request.ApiErrorRequest;
import org.neonsis.socialnetwork.rest.payload.request.ApiValidationErrorRequest;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@ControllerAdvice
@Order(Ordered.HIGHEST_PRECEDENCE)
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(
            MethodArgumentNotValidException ex,
            HttpHeaders headers,
            HttpStatus status,
            WebRequest request) {
        List<ValidationError> errors = ex.getBindingResult().getFieldErrors().stream()
                .map(fieldError -> new ValidationError(fieldError.getField(), fieldError.getDefaultMessage()))
                .collect(Collectors.toList());

        ApiValidationErrorRequest apiError = new ApiValidationErrorRequest(BAD_REQUEST);
        apiError.setMessage("Validation error");
        apiError.setDetails(errors);

        return buildResponseEntity(apiError);
    }

    @ExceptionHandler(RecordNotFoundException.class)
    protected ResponseEntity<Object> handleRecordNotFound(RecordNotFoundException ex) {
        ApiErrorRequest apiError = new ApiErrorRequest(NOT_FOUND);
        apiError.setMessage(ex.getMessage());
        return buildResponseEntity(apiError);
    }

    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        String error = "Malformed JSON request";
        return buildResponseEntity(new ApiErrorRequest(BAD_REQUEST, error));
    }

    private ResponseEntity<Object> buildResponseEntity(ApiErrorRequest apiError) {
        return new ResponseEntity<>(apiError, apiError.getStatus());
    }

}
