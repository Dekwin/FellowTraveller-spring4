package com.fellowtraveler.exeptions;

/**
 * Created by igorkasyanenko on 02.03.17.
 */
public class ProfileNotFoundException extends Exception{
    public ProfileNotFoundException(String message) {
        super(message);
    }
}
