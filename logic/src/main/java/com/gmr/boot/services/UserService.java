package com.gmr.boot.services;


import com.gmr.boot.domain.AuthorizableUserProfile;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetailsService;


public interface UserService extends UserDetailsService {

    Page<AuthorizableUserProfile> listAllUsers(Pageable pagingCriteria);

    void deleteUserById(String id);

}
