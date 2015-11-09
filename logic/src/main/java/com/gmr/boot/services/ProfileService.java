package com.gmr.boot.services;

import com.gmr.boot.BootException;
import com.gmr.boot.domain.CredentialsUserProfile;
import com.gmr.boot.domain.UserProfile;


/**
 * Service for interacting with a user's own profile.
 */
public interface ProfileService {

    UserProfile getOwnProfile() throws BootException;

    void register(CredentialsUserProfile profile) throws BootException;

    void changePassword(char[] newPassword) throws BootException;

    void changePassword(char[] oldPassword, char[] newPassword) throws BootException;

}
