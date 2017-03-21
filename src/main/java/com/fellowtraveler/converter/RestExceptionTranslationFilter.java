package com.fellowtraveler.converter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.access.ExceptionTranslationFilter;
import org.springframework.security.web.savedrequest.RequestCache;
import org.springframework.stereotype.Component;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Created by igorkasyanenko on 12.03.17.
 */

public class RestExceptionTranslationFilter extends ExceptionTranslationFilter {
    public static final Logger LOGGER = LoggerFactory.getLogger(RestExceptionTranslationFilter.class);

    public RestExceptionTranslationFilter(AuthenticationEntryPoint authenticationEntryPoint) {
        super(authenticationEntryPoint);
    }

    public RestExceptionTranslationFilter(AuthenticationEntryPoint authenticationEntryPoint, RequestCache requestCache) {
        super(authenticationEntryPoint, requestCache);
    }

    @Override
    protected void sendStartAuthentication(HttpServletRequest req, HttpServletResponse resp, FilterChain chain, AuthenticationException reason)
            throws ServletException, IOException {


        boolean isAjax = "XMLHttpRequest".equals(req.getHeader("X-Requested-With"));

        if (isAjax) {

            String jsonObject = "{\"message\":\"Please login first.\"," + "\"access-denied\":true,\"cause\":\"AUTHENTICATION_FAILURE\"}";
            String contentType = "application/json";
            resp.setContentType(contentType);
            PrintWriter out = resp.getWriter();
            out.print(jsonObject);
            out.flush();
            out.close();
            return;
        }

        super.sendStartAuthentication(req, resp, chain, reason);
    }

}

