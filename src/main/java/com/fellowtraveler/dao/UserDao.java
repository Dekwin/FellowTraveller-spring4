package com.fellowtraveler.dao;

/**
 * Created by igorkasyanenko on 12.12.16.
 */


import com.fellowtraveler.model.User;

import java.util.List;


public interface UserDao {

    User findById(int id);

    User findBySSO(String sso);

    void save(User user);

    void deleteBySSO(String sso);

    List<User> findAllUsers();

}
