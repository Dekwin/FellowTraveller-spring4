package com.fellowtraveler.dao;

import com.fellowtraveler.model.UserProfile;

import java.util.List;

/**
 * Created by igorkasyanenko on 12.12.16.
 */



public interface UserProfileDao {

    List<UserProfile> findAll();

    UserProfile findByType(String type);

    UserProfile findById(int id);
}
