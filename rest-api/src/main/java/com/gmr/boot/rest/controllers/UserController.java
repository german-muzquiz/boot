package com.gmr.boot.rest.controllers;

import com.gmr.boot.rest.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.security.web.bind.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping(value = Constants.API_PREFIX + "/user")
public class UserController {

    @Autowired
    private UserDetailsManager userDetailsManager;

    @Autowired
    private PasswordEncoder passwordEncoder;


    @RequestMapping(method = RequestMethod.GET, params = "currentUser")
    public @ResponseBody User getCurrentUser(@AuthenticationPrincipal User currentUser) {
        return currentUser;
    }


    @RequestMapping(value = "/password", method = RequestMethod.POST)
    public @ResponseBody boolean changePassword(
            @RequestParam("oldPassword") CharSequence oldPassword,
            @RequestParam("newPassword") CharSequence newPassword) {

        userDetailsManager.changePassword(oldPassword.toString(), passwordEncoder.encode(newPassword));
        return true;
    }

}
