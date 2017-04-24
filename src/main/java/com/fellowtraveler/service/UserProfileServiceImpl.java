package com.fellowtraveler.service;

import com.fellowtraveler.dao.UserProfileDao;
import com.fellowtraveler.model.UserProfile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

/**
 * Created by igorkasyanenko on 12.12.16.
 */


@Service("userProfileService")
@org.springframework.transaction.annotation.Transactional(value = "hibernate-transaction")
public class UserProfileServiceImpl implements UserProfileService {

    @Autowired
    UserProfileDao dao;

    public UserProfile findById(int id) {
        return dao.findById(id);
    }

    public UserProfile findByType(String type) {
        return dao.findByType(type);
    }

    public List<UserProfile> findAll() {
        return dao.findAll();
    }
}