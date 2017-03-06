package com.fellowtraveler.converter;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.exc.UnrecognizedPropertyException;
import com.fellowtraveler.model.User;
import com.fellowtraveler.model.errors.ErrorMessage;
import com.fellowtraveler.model.errors.JSONException;
import com.fellowtraveler.model.jwt2.AccountCredentials;
import com.fellowtraveler.service.TokenAuthenticationService;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by igorkasyanenko on 04.03.17.
 */


public class JWTLoginFilter extends AbstractAuthenticationProcessingFilter {


   // @Autowired

    private TokenAuthenticationService tokenAuthenticationService;

    public JWTLoginFilter(String url, AuthenticationManager authenticationManager) {
        super(new AntPathRequestMatcher(url));
        setAuthenticationManager(authenticationManager);
        tokenAuthenticationService = new TokenAuthenticationService();
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws AuthenticationException, IOException, ServletException {
        try {
            AccountCredentials credentials = new ObjectMapper().readValue(httpServletRequest.getInputStream(), AccountCredentials.class);
            UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(credentials.getSsoId(), credentials.getPassword());
            return getAuthenticationManager().authenticate(token);
        } catch (UnrecognizedPropertyException ex){
            httpServletResponse.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
            httpServletResponse.getWriter().write(convertObjectToJson(new JSONException(0,ex.getMessage())));
            return null;
        }
    }

    public String convertObjectToJson(Object object) throws JsonProcessingException {
        if (object == null) {
            return null;
        }
        ObjectMapper mapper = new ObjectMapper();
        return mapper.writeValueAsString(object);
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authentication)
            throws IOException, ServletException {

        System.out.println("!authentication");
        System.out.println(authentication);
        System.out.println(authentication.getPrincipal());
        System.out.println(authentication.getDetails());
        System.out.println(((User)authentication.getPrincipal()).getEmail());

        String name = authentication.getName();
        tokenAuthenticationService.addAuthentication(response, name);
    }
}