package com.gmr.boot.services;


import com.gmr.boot.domain.Role;

import java.util.Collections;
import java.util.List;


public abstract class ServiceConstants {

    private ServiceConstants() {}

    public static final String ROLE_ADMIN = "ADMIN";
    public static final String ROLE_USER = "USER";

    public static final List<Role> DEFAULT_USER_ROLES = Collections.singletonList(new Role(ROLE_USER));
    public static final List<String> DEFAULT_USER_ROLE_NAMES = Collections.singletonList(ROLE_USER);

}
