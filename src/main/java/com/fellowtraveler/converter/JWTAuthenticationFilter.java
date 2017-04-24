package com.fellowtraveler.converter;

/**
 * Created by igorkasyanenko on 04.03.17.
 */

import com.fellowtraveler.service.TokenAuthenticationService;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.GenericFilterBean;
import org.springframework.security.core.Authentication;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class JWTAuthenticationFilter extends GenericFilterBean {

    private final String signinPath = "/signin";
    private final String signupPath = "/signup";
    private Pattern pattern = Pattern.compile("/static/.*");


    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain) throws IOException, ServletException {

        HttpServletRequest httpRequest = (HttpServletRequest) request;
        String url = httpRequest.getServletPath();
        switch (url) {
            case signinPath:
                request.getRequestDispatcher(httpRequest.getServletPath()).forward(request, response);
                break;
            case signupPath:
                request.getRequestDispatcher(httpRequest.getServletPath()).forward(request, response);
                break;
            default:

                Matcher matcher = pattern.matcher(url);

                if (matcher.matches()) {
                    request.getRequestDispatcher(httpRequest.getServletPath()).forward(request, response);
                } else {
                    filterChain.doFilter(request, response);
                }
                break;
        }

    }
}