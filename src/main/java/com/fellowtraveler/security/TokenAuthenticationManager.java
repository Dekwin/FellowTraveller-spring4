package com.fellowtraveler.security;

import com.fellowtraveler.model.jwtauth.TokenAuthentication;
import com.fellowtraveler.model.userdetails.CustomUserDetails;
import com.fellowtraveler.service.CustomUserDetailsService;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.impl.DefaultClaims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;
import java.util.Date;

/**
 * Created by igorkasyanenko on 21.03.17.
 */
@Service
public class TokenAuthenticationManager implements AuthenticationManager {

    @Autowired
    private UserDetailsService userDetailsService;

    private final String key = "key123";

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        try {
            if (authentication instanceof TokenAuthentication) {
                TokenAuthentication readyTokenAuthentication = processAuthentication((TokenAuthentication) authentication);
                return readyTokenAuthentication;
            } else {
                authentication.setAuthenticated(false);
                return authentication;
            }
        } catch (AuthenticationException ex) {
            //  if(ex instanceof AuthenticationServiceException)
            throw ex;
        }

        //return authentication;
    }

    private TokenAuthentication processAuthentication(TokenAuthentication authentication) throws AuthenticationException {
        String token = authentication.getToken();


        DefaultClaims claims;
        try {
            claims = (DefaultClaims) Jwts.parser().setSigningKey(key).parse(token).getBody();
        } catch (Exception ex) {
            throw new AuthenticationServiceException("Token corrupted");
        }
        if (claims.get("TOKEN_EXPIRATION_DATE", Long.class) == null)
            throw new AuthenticationServiceException("Invalid token");
        Date expiredDate = new Date(claims.get("TOKEN_EXPIRATION_DATE", Long.class));
        if (expiredDate.after(new Date()))
            return buildFullTokenAuthentication(authentication, claims);
        else
            throw new AuthenticationServiceException("Token expired date error");
    }

    private TokenAuthentication buildFullTokenAuthentication(TokenAuthentication authentication, DefaultClaims claims) {
        CustomUserDetails user = (CustomUserDetails) userDetailsService.loadUserByUsername(claims.get("ssoId", String.class));
        if (user.isEnabled()) {
            Collection<GrantedAuthority> authorities = user.getAuthorities();
            TokenAuthentication fullTokenAuthentication =
                    new TokenAuthentication(authentication.getToken(), authorities, true, user);
            return fullTokenAuthentication;
        } else {
            throw new AuthenticationServiceException("User disabled");
        }
    }
}