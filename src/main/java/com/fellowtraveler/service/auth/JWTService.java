package com.fellowtraveler.service.auth;

import com.fellowtraveler.model.User;
import com.fellowtraveler.model.userdetails.CustomUserDetails;

import javax.servlet.http.HttpServletResponse;

/**
 * Created by igorkasyanenko on 21.03.17.
 */
public interface JWTService {
    String getToken(CustomUserDetails user)  throws Exception;
    User addAuthentication(HttpServletResponse response, String ssoId, String password) throws Exception;
}
