package com.fellowtraveler.service;

import com.fellowtraveler.model.UserProfile;

import java.util.List;

/**
 * Created by igorkasyanenko on 12.12.16.
 */



public interface UserProfileService {

    UserProfile findById(int id);

    UserProfile findByType(String type);

    List<UserProfile> findAll();

}