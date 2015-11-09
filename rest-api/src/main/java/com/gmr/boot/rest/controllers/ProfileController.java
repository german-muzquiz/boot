package com.gmr.boot.rest.controllers;

import com.gmr.boot.BootException;
import com.gmr.boot.domain.CredentialsUserProfile;
import com.gmr.boot.domain.UserProfile;
import com.gmr.boot.rest.Constants;
import com.gmr.boot.services.ProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;


/**
 * Operations for a user's own profile.
 */
@RestController
@RequestMapping(value = Constants.API_PREFIX + "/profile")
public class ProfileController {

    @Autowired
    @Qualifier("profileService")
    private ProfileService profileService;


    @RequestMapping(method = RequestMethod.POST)
    public @ResponseBody boolean register(@RequestBody CredentialsUserProfile user) throws BootException {
        profileService.register(user);
        return true;
    }


    @RequestMapping(method = RequestMethod.GET)
    public @ResponseBody UserProfile getCurrentUser() throws BootException {
        return profileService.getOwnProfile();
    }


    @RequestMapping(value = "/password", method = RequestMethod.POST)
    public @ResponseBody boolean changePassword(
            @RequestParam("oldPassword") char[] oldPassword,
            @RequestParam("newPassword") char[] newPassword) throws BootException {

        profileService.changePassword(oldPassword, newPassword);
        return true;
    }

}
