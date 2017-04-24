package com.fellowtraveler.exceptions;

/**
 * Created by igorkasyanenko on 02.03.17.
 */
public class FailedToLoginException extends Exception {
    public FailedToLoginException(String message) {
        super(message);
    }
}
