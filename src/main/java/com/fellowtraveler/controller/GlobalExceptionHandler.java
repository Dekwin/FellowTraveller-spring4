package com.fellowtraveler.controller;

import com.fellowtraveler.exeptions.ProfileNotFoundException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import static org.springframework.http.HttpStatus.NOT_FOUND;

/**
 * Created by igorkasyanenko on 02.03.17.
 */
@ControllerAdvice
public class GlobalExceptionHandler {
    @ResponseStatus(NOT_FOUND)
    @ExceptionHandler(ProfileNotFoundException.class)
    public void profileNotFound() {
    }
}