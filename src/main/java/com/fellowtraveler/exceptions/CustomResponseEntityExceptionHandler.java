package com.fellowtraveler.exceptions;

import com.fellowtraveler.model.errors.ErrorMessage;
import com.fellowtraveler.model.errors.JSONException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by igorkasyanenko on 05.03.17.
 */
@ControllerAdvice
public class CustomResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {


    @ExceptionHandler(value = { IllegalArgumentException.class })
    protected ResponseEntity<Object> handleConflict(RuntimeException ex, WebRequest request) {
        return handleExceptionInternal(ex, new JSONException(0,ex.getMessage()),
                new HttpHeaders(), HttpStatus.CONFLICT, request);

    }





    @ExceptionHandler(value = { ConstraintViolationException.class })
    protected ResponseEntity<Object> handleConstraintViolationException(ConstraintViolationException ex, WebRequest request) {


        List<String> errors = new ArrayList<>(ex.getConstraintViolations().size());
        String error;
        for (ConstraintViolation cError : ex.getConstraintViolations()) {

            errors.add(cError.getPropertyPath().toString() +" "+cError.getMessage());

        }

        ErrorMessage errorMessage = new ErrorMessage(errors);
        return new ResponseEntity(errorMessage, new HttpHeaders(), HttpStatus.UNPROCESSABLE_ENTITY);

//        return handleExceptionInternal(ex, new JSONException(0,ex.getMessage()),
//                new HttpHeaders(), HttpStatus.CONFLICT, request);
    }




    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        List<FieldError> fieldErrors = ex.getBindingResult().getFieldErrors();
        List<ObjectError> globalErrors = ex.getBindingResult().getGlobalErrors();
        List<String> errors = new ArrayList<>(fieldErrors.size() + globalErrors.size());
        String error;
        for (FieldError fieldError : fieldErrors) {
            error = fieldError.getField() + ", " + fieldError.getDefaultMessage();
            errors.add(error);
        }
        for (ObjectError objectError : globalErrors) {
            error = objectError.getObjectName() + ", " + objectError.getDefaultMessage();
            errors.add(error);
        }
        ErrorMessage errorMessage = new ErrorMessage(errors);
        return new ResponseEntity(errorMessage, headers, status);
    }

    @Override
    protected ResponseEntity<Object> handleHttpMediaTypeNotSupported(HttpMediaTypeNotSupportedException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        String unsupported = "Unsupported content type: " + ex.getContentType();
        String supported = "Supported content types: " + MediaType.toString(ex.getSupportedMediaTypes());
        ErrorMessage errorMessage = new ErrorMessage(unsupported, supported);
        return new ResponseEntity(errorMessage, headers, status);
    }

    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        Throwable mostSpecificCause = ex.getMostSpecificCause();
        ErrorMessage errorMessage;
        if (mostSpecificCause != null) {
            String exceptionName = mostSpecificCause.getClass().getName();
            String message = mostSpecificCause.getMessage();
            errorMessage = new ErrorMessage(exceptionName, message);
        } else {
            errorMessage = new ErrorMessage(ex.getMessage());
        }
        return new ResponseEntity(errorMessage, headers, status);
    }


    @ExceptionHandler(value = { AuthenticationException.class })
    protected ResponseEntity<Object> handleAuthenticationException(AuthenticationException ex, WebRequest request) {


        List<String> errors = new ArrayList<>();


        ErrorMessage errorMessage = new ErrorMessage(errors);
        return new ResponseEntity(errorMessage, new HttpHeaders(), HttpStatus.UNPROCESSABLE_ENTITY);

//        return handleExceptionInternal(ex, new JSONException(0,ex.getMessage()),
//                new HttpHeaders(), HttpStatus.CONFLICT, request);
    }


    @ExceptionHandler(value = { BadCredentialsException.class })
    protected ResponseEntity<Object> handleCred(BadCredentialsException ex) {


        List<String> errors = new ArrayList<>();


        ErrorMessage errorMessage = new ErrorMessage(errors);
        return new ResponseEntity(errorMessage, new HttpHeaders(), HttpStatus.UNPROCESSABLE_ENTITY);

//        return handleExceptionInternal(ex, new JSONException(0,ex.getMessage()),
//                new HttpHeaders(), HttpStatus.CONFLICT, request);
    }


}