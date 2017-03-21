package com.fellowtraveler.model.userdetails;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

/**
 * Created by igorkasyanenko on 07.03.17.
 */
public class CustomUserDetails extends User {

    private com.fellowtraveler.model.User user;

    public void setUser(com.fellowtraveler.model.User user) {
        this.user = user;
    }

    public CustomUserDetails(String username, String password, Collection<? extends GrantedAuthority> authorities) {
        super(username, password, authorities);
    }

    public CustomUserDetails(String username, String password, boolean enabled, boolean accountNonExpired, boolean credentialsNonExpired, boolean accountNonLocked, Collection<? extends GrantedAuthority> authorities) {
        super(username, password, enabled, accountNonExpired, credentialsNonExpired, accountNonLocked, authorities);
    }

    public com.fellowtraveler.model.User getUser(){
        return user;
    }

    @Override
    public String getUsername(){
        return user.getSsoId();
    }

    @Override
    public String getPassword(){
        return user.getPassword();
    }

}