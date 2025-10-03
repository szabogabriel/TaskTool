package com.ibm.sk.tasktool.exception;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.validation.method.ParameterValidationResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.HandlerMethodValidationException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.ArrayList;
import java.util.List;

@ControllerAdvice
public class TaskToolExceptionHandler extends ResponseEntityExceptionHandler {  // we extend this common exception handler and customize what we want

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler( /*produces = MediaType.TEXT_PLAIN_VALUE*/ )
    public ResponseEntity<String> handleEntityNotFoundException(EntityNotFoundException entityNotFoundException) {
        ResponseEntity<String> responseEntity = new ResponseEntity<>("Entity with provided ID does not exist.", HttpStatus.NOT_FOUND);
        return responseEntity;
    }

    // it seems that in Spring 6 they have introduced this kind of exception for Controller method parameters
    // so if the method parameter is a DTO then MethodArgumentNotValidException is thrown as before
    // but when it is direct primitive value of Controller method parameter, then HandlerMethodValidationException is thrown
    // which has totally different structure (it is not compatible with BindingResult unfortunately)
    @Override
    protected ResponseEntity<Object> handleHandlerMethodValidationException(HandlerMethodValidationException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        List<ParameterValidationResult> parameterValidationResults = ex.getParameterValidationResults();

        List<ErrorModel> validationErrorModels = new ArrayList<ErrorModel>();

        parameterValidationResults.forEach(result -> {
            result.getResolvableErrors().forEach(error -> {
                ErrorModel validationErrorModel = ErrorModel
                        .builder()
                        .code(error.getCodes()[0].toString())
                        .source(result.getMethodParameter() + "." + error.getArguments()[0])
                        .detail(error.getDefaultMessage())
                        .build();

                validationErrorModels.add(validationErrorModel);
            });

            result.getMethodParameter();
        });

        ErrorResponseModel validation = ErrorResponseModel
                .builder()
                .errorModels(validationErrorModels)
                .type("VALIDATION")
                .build();

        return new ResponseEntity<>(validation, status);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        List<ErrorModel> errorModels = processErrors(ex);
        ErrorResponseModel validation = ErrorResponseModel
                .builder()
                .errorModels(errorModels)
                .type("VALIDATION")
                .build();

        return new ResponseEntity<>(validation, status);
    }

    private List<ErrorModel> processErrors(MethodArgumentNotValidException e) {
        List<ErrorModel> validationErrorModels = new ArrayList<ErrorModel>();
        for (FieldError fieldError : e.getBindingResult().getFieldErrors()) {
            ErrorModel validationErrorModel = ErrorModel
                    .builder()
                    .code(fieldError.getCode())
                    .source(fieldError.getObjectName() + "/" + fieldError.getField())
                    .detail(fieldError.getField() + " " + fieldError.getDefaultMessage())
                    .build();
            validationErrorModels.add(validationErrorModel);
        }
        return validationErrorModels;
    }
}
