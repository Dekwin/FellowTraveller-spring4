package com.fellowtraveler.service;

/**
 * Created by igorkasyanenko on 12.12.16.
 */

import com.fellowtraveler.dao.UserDao;
import com.fellowtraveler.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service("userService")
@Transactional
public class UserServiceImpl implements UserService{

    @Autowired
    private UserDao dao;

   // @Autowired
   // private PasswordEncoder passwordEncoder;

    public User findById(int id) {
        return dao.findById(id);
    }

    public User findBySSO(String sso) {
        User user = dao.findBySSO(sso);
        return user;
    }

    public void saveUser(User user) {
        //user.setPassword(passwordEncoder.encode(user.getPassword()));
        dao.save(user);
    }


    public User updateUser(User user) {
        User entity = dao.findById(user.getId());
        if(entity!=null){
            if(user.getSsoId()!=null&&!user.getSsoId().equals(""))
            entity.setSsoId(user.getSsoId());
//            if(!user.getPassword().equals(entity.getPassword())){
//              //  entity.setPassword(passwordEncoder.encode(user.getPassword()));
//            }

            if(user.getFirstName()!=null&&!user.getFirstName().equals(""))
            entity.setFirstName(user.getFirstName());
            if(user.getLastName()!=null&&!user.getLastName().equals(""))
            entity.setLastName(user.getLastName());
            if(user.getEmail()!=null&&!user.getEmail().equals(""))
            entity.setEmail(user.getEmail());
            if(user.getGender()!=null&&!user.getGender().equals(""))
            entity.setGender(user.getGender());
            if(user.getUserProfiles()!=null)
            entity.setUserProfiles(user.getUserProfiles());
            if(user.getCars()!=null)
            entity.setCars(user.getCars());
            if(user.getImageUrl()!=null&&!user.getImageUrl().equals(""))
            entity.setImageUrl(user.getImageUrl());
        }
        return entity;
    }


    public void deleteUserBySSO(String sso) {
        dao.deleteBySSO(sso);
    }

    public List<User> findAllUsers() {
        return dao.findAllUsers();
    }

    public boolean isUserSSOUnique(Integer id, String sso) {
        User user = findBySSO(sso);
        return ( user == null || ((id != null) && (user.getId() == id)));
    }

}