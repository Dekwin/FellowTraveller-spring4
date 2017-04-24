package com.fellowtraveler.controller;

import com.fellowtraveler.model.User;
import com.fellowtraveler.model.jwtauth.AccountCredentials;
import com.fellowtraveler.model.jwtauth.AuthenticatedUser;
import com.fellowtraveler.model.map.CollectionData;
import com.fellowtraveler.model.map.Route;
import com.fellowtraveler.service.map.PointService;
import com.fellowtraveler.service.UserService;
import com.fellowtraveler.service.auth.JWTService;
import com.fellowtraveler.model.map.RoutePoint;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Optional;

/**
 * Created by igorkasyanenko on 27.02.17.
 */
//@EnableAsync
@Controller
@RequestMapping("/")
@SessionAttributes("roles")
public class AppController {

    @Autowired
    UserService userService;

    @Autowired
    MessageSource messageSource;

    @Autowired
    JWTService jwtService;

    @Autowired
    PointService pointService;


    @RequestMapping("/rest/usersnull")
    public @ResponseBody String getUsers1() {

        return "{\"users\":[{\"firstname\":\"Richard\", \"lastname\":\"Feynman\"}," +
                "{\"firstname\":\"Marie\",\"lastname\":\"Curie\"}]}";
    }


    @RequestMapping(value = "/users", method = RequestMethod.GET)
    public @ResponseBody
    ResponseEntity<User> getUsers(@RequestParam("id") Optional<Long> userId) {
        User foundUser = userService.findById(userId.get().intValue());
        if(foundUser != null) {
            return new ResponseEntity<User>(foundUser, HttpStatus.OK);
        }else {
           return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @RequestMapping(value = { "/signup" }, method = RequestMethod.POST)
    public ResponseEntity<User> signup(
            @Valid @RequestBody User user, BindingResult bindingResult,  HttpServletResponse response) throws Exception {

        if(!userService.isUserSSOUnique(user.getId(), user.getSsoId())){
            FieldError ssoError = new FieldError("user","ssoId",messageSource.getMessage("non.unique.ssoId", new String[]{user.getSsoId()}, Locale.getDefault()));
            bindingResult.addError(ssoError);
            IllegalArgumentException error = new IllegalArgumentException(ssoError.getDefaultMessage());
          // new  MethodArgumentNotValidException().getBindingResult().addError(ssoError);
            throw error;
            //return new ResponseEntity<User>(user, HttpStatus.FORBIDDEN);
        }

        userService.saveUser(user);

        String name = user.getSsoId();

        jwtService.addAuthentication(response,user.getSsoId(),user.getPassword());

        SecurityContextHolder.getContext().setAuthentication(new AuthenticatedUser(name));

        return new ResponseEntity<User>(user, HttpStatus.CREATED);
    }

    @RequestMapping(value = { "/signin" }, method = RequestMethod.POST)
    public ResponseEntity<User> signin(
             @RequestBody AccountCredentials credentials,  HttpServletResponse response) throws Exception {


        User user = jwtService.addAuthentication(response,credentials.getSsoId(),credentials.getPassword());


        return new ResponseEntity<User>(user, HttpStatus.OK);
    }



}