package com.fellowtraveler.service;

/**
 * Created by igorkasyanenko on 12.12.16.
 */


import com.fellowtraveler.model.User;

import java.util.List;


public interface UserService {

    User findById(int id);

    User findBySSO(String sso);

    void saveUser(User user);

    User updateUser(User user);

    void deleteUserBySSO(String sso);

    List<User> findAllUsers();

    boolean isUserSSOUnique(Integer id, String sso);

}