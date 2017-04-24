package com.fellowtraveler.service.auth;

import com.fellowtraveler.model.User;
import com.fellowtraveler.model.userdetails.CustomUserDetails;
import com.fellowtraveler.service.CustomUserDetailsService;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletResponse;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by igorkasyanenko on 21.03.17.
 */
@Service
@Transactional(value = "hibernate-transaction")
public class JWTServiceImpl implements JWTService {

    @Autowired
    private UserDetailsService userDetailsService;

    private String tokenPrefix = "Bearer";
    private String headerString = "Authorization";
    private final String key = "key123";

    @Override
    public String getToken(CustomUserDetails user) throws Exception {
        Map<String, Object> tokenData = new HashMap<>();
        tokenData.put("clientType", "user");
        tokenData.put("userID", user.getUser().getId().toString());
        tokenData.put("ssoId", user.getUser().getSsoId());
        tokenData.put("TOKEN_CREATE_DATE", new Date().getTime());
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.YEAR, 100);
        tokenData.put("TOKEN_EXPIRATION_DATE", calendar.getTime());
        JwtBuilder jwtBuilder = Jwts.builder();
        jwtBuilder.setExpiration(calendar.getTime());
        jwtBuilder.setClaims(tokenData);

        String token = jwtBuilder.signWith(SignatureAlgorithm.HS512, key).compact();
        return token;

    }

    @Override
    public User addAuthentication(HttpServletResponse response, String ssoId, String password) throws Exception {
        // We generate a token now.
        if (ssoId == null || password == null)
            return null;
        CustomUserDetails user = (CustomUserDetails) userDetailsService.loadUserByUsername(ssoId);
        if (user != null && password.equals(user.getPassword())) {
            String token = getToken(user);
            response.addHeader(headerString, tokenPrefix + " " + token);
        } else {
            throw new Exception("Authentication error");
        }

        return user.getUser();
    }

}