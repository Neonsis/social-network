package org.neonsis.socialnetwork.rest.controller;

import org.neonsis.socialnetwork.exception.EntityNotFoundException;
import org.neonsis.socialnetwork.exception.InternalServerErrorException;
import org.neonsis.socialnetwork.exception.InvalidWorkFlowException;
import org.neonsis.socialnetwork.rest.exception.model.ValidationError;
import org.neonsis.socialnetwork.rest.model.request.ApiErrorRequest;
import org.neonsis.socialnetwork.rest.model.request.ApiValidationErrorRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

import static org.springframework.http.HttpStatus.*;

@ControllerAdvice
@Order(Ordered.HIGHEST_PRECEDENCE)
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

    private static final Logger logger = LoggerFactory.getLogger(RestExceptionHandler.class);

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(
            MethodArgumentNotValidException ex,
            HttpHeaders headers,
            HttpStatus status,
            WebRequest request) {
        logger.warn("Responding with validation error.  Message - {}", ex.getMessage());
        List<ValidationError> errors = ex.getBindingResult().getFieldErrors().stream()
                .map(fieldError -> new ValidationError(fieldError.getField(), fieldError.getDefaultMessage()))
                .collect(Collectors.toList());

        ApiValidationErrorRequest apiError = new ApiValidationErrorRequest(BAD_REQUEST);
        apiError.setMessage("Validation error");
        apiError.setDetails(errors);

        return buildResponseEntity(apiError);
    }

    @ExceptionHandler(InvalidWorkFlowException.class)
    protected ResponseEntity<Object> handleInvalidWorkflowException(InvalidWorkFlowException ex) {
        logger.warn("Responding with invalid workflow error.  Message - {}", ex.getMessage());
        ApiErrorRequest apiError = new ApiErrorRequest(BAD_REQUEST);
        apiError.setMessage(ex.getMessage());
        return buildResponseEntity(apiError);
    }

    @ExceptionHandler(InternalServerErrorException.class)
    protected ResponseEntity<Object> handleInternalServerException(InternalServerErrorException ex) {
        logger.error("Responding with internal server error.  Message - {}", ex.getMessage());
        ApiErrorRequest apiError = new ApiErrorRequest(INTERNAL_SERVER_ERROR);
        apiError.setMessage("Server error");
        return buildResponseEntity(apiError);
    }

    @ExceptionHandler(EntityNotFoundException.class)
    protected ResponseEntity<Object> handleRecordNotFound(EntityNotFoundException ex) {
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
