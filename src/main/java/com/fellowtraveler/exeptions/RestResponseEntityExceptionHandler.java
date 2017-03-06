package com.fellowtraveler.exeptions;

import com.fellowtraveler.model.errors.JSONException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

/**
 * Created by igorkasyanenko on 05.03.17.
 */
@ControllerAdvice
public class RestResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

//
//    @ExceptionHandler(value = { IllegalArgumentException.class, IllegalStateException.class, BadCredentialsException.class })
//    protected ResponseEntity<Object> handleConflict(RuntimeException ex, WebRequest request) {
//        //String bodyOfResponse = ex.getMessage();
//        return handleExceptionInternal(ex, new JSONException(0,ex.getMessage()),
//                new HttpHeaders(), HttpStatus.CONFLICT, request);
//    }
}