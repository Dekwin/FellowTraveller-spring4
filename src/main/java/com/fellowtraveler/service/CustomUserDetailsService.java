package com.fellowtraveler.service;

/**
 * Created by igorkasyanenko on 11.12.16.
 */

import com.fellowtraveler.model.User;
import com.fellowtraveler.model.UserProfile;
import com.fellowtraveler.model.userdetails.CustomUserDetails;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;


@Service("customUserDetailsService")
@Transactional(value = "hibernate-transaction")
public class CustomUserDetailsService implements UserDetailsService {

    static final Logger logger = LoggerFactory.getLogger(CustomUserDetailsService.class);

    @Autowired
    private UserService userService;

    @Transactional(readOnly = true, value = "hibernate-transaction")
    public UserDetails loadUserByUsername(String ssoId)
            throws UsernameNotFoundException {
        User user = userService.findBySSO(ssoId);
        logger.info("User : {}", user);
        if (user == null) {
            logger.info("User not found");
            throw new UsernameNotFoundException("Username not found");
        }
        CustomUserDetails cud = new CustomUserDetails(user.getSsoId(), user.getPassword(),
                true, true, true, true, getGrantedAuthorities(user));

        cud.setUser(user);
        return cud;
    }


    private List<GrantedAuthority> getGrantedAuthorities(User user) {
        List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();


        for (UserProfile userProfile : user.getUserProfiles()) {
            logger.info("UserProfile : {}", userProfile);
            authorities.add(new SimpleGrantedAuthority("ROLE_" + userProfile.getType()));
        }
        logger.info("authorities : {}", authorities);
        return authorities;
    }

}
