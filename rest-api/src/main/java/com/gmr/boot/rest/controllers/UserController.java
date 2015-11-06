package com.gmr.boot.rest.controllers;

import com.gmr.boot.rest.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.security.web.bind.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping(value = Constants.API_PREFIX + "/user")
public class UserController {

    @Autowired
    private UserDetailsManager userDetailsManager;

    @Autowired
    private PasswordEncoder passwordEncoder;


    @RequestMapping(method = RequestMethod.GET, params = "currentUser")
    public @ResponseBody OAuth2Authentication getCurrentUser(@AuthenticationPrincipal OAuth2Authentication currentUser) {
        return currentUser;
    }


    @RequestMapping(method = RequestMethod.POST, value = "/password")
    public @ResponseBody boolean changePassword(
            @RequestParam("oldPassword") CharSequence oldPassword,
            @RequestParam("newPassword") CharSequence newPassword) {

        userDetailsManager.changePassword(oldPassword.toString(), passwordEncoder.encode(newPassword));
        return true;
    }

}
