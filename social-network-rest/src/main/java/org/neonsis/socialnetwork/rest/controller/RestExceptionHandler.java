package org.neonsis.socialnetwork.rest.controller;

import lombok.RequiredArgsConstructor;
import org.neonsis.socialnetwork.exception.EntityNotFoundException;
import org.neonsis.socialnetwork.exception.InternalServerErrorException;
import org.neonsis.socialnetwork.exception.InvalidWorkFlowException;
import org.neonsis.socialnetwork.rest.exception.model.ValidationError;
import org.neonsis.socialnetwork.rest.model.response.ApiErrorResponse;
import org.neonsis.socialnetwork.rest.model.response.ApiValidationErrorResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.MessageSource;
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
@RequiredArgsConstructor
@Order(Ordered.HIGHEST_PRECEDENCE)
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

    private static final Logger logger = LoggerFactory.getLogger(RestExceptionHandler.class);

    private final MessageSource messageSource;

    private static final String VALIDATION_ERROR = "config.data.exceptions.validation_error";

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(
            MethodArgumentNotValidException ex,
            HttpHeaders headers,
            HttpStatus status,
            WebRequest request
    ) {
        logger.warn("Responding with validation error.  Message - {}", ex.getMessage());
        List<ValidationError> errors = ex.getBindingResult().getFieldErrors().stream()
                .map(fieldError -> new ValidationError(fieldError.getField(), fieldError.getDefaultMessage()))
                .collect(Collectors.toList());

        ApiValidationErrorResponse apiError = new ApiValidationErrorResponse(BAD_REQUEST);
        apiError.setMessage(messageSource.getMessage(VALIDATION_ERROR, null, request.getLocale()));
        apiError.setDetails(errors);

        return buildResponseEntity(apiError);
    }

    @ExceptionHandler(InvalidWorkFlowException.class)
    protected ResponseEntity<Object> handleInvalidWorkflowException(InvalidWorkFlowException ex) {
        logger.warn("Responding with invalid workflow error.  Message - {}", ex.getMessage());
        ApiErrorResponse apiError = new ApiErrorResponse(BAD_REQUEST);
        apiError.setMessage(ex.getMessage());
        return buildResponseEntity(apiError);
    }

    @ExceptionHandler(InternalServerErrorException.class)
    protected ResponseEntity<Object> handleInternalServerException(InternalServerErrorException ex) {
        logger.error("Responding with internal server error.  Message - {}", ex.getMessage());
        ApiErrorResponse apiError = new ApiErrorResponse(INTERNAL_SERVER_ERROR);
        apiError.setMessage("Server error");
        return buildResponseEntity(apiError);
    }

    @ExceptionHandler(EntityNotFoundException.class)
    protected ResponseEntity<Object> handleRecordNotFound(EntityNotFoundException ex) {
        ApiErrorResponse apiError = new ApiErrorResponse(NOT_FOUND);
        apiError.setMessage(ex.getMessage());
        return buildResponseEntity(apiError);
    }

    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        String error = "Malformed JSON request";
        return buildResponseEntity(new ApiErrorResponse(BAD_REQUEST, error));
    }

    private ResponseEntity<Object> buildResponseEntity(ApiErrorResponse apiError) {
        return new ResponseEntity<>(apiError, apiError.getStatus());
    }

}
