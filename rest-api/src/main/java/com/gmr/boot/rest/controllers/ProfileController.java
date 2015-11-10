package com.gmr.boot.rest.controllers;

import com.gmr.boot.BootException;
import com.gmr.boot.domain.CredentialsUserProfile;
import com.gmr.boot.domain.UserProfile;
import com.gmr.boot.rest.RestConstants;
import com.gmr.boot.services.ProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;


/**
 * Operations for a user's own profile.
 */
@RestController
@RequestMapping(value = RestConstants.API_PREFIX + "/profile")
public class ProfileController {

    @Autowired
    @Qualifier("profileService")
    private ProfileService profileService;


    @RequestMapping(method = RequestMethod.POST)
    public @ResponseBody boolean register(@RequestBody CredentialsUserProfile user, HttpServletRequest request) throws BootException {
        profileService.register(user, request);
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
