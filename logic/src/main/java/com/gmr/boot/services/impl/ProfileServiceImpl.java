package com.gmr.boot.services.impl;

import com.gmr.boot.BootException;
import com.gmr.boot.domain.CredentialsUserProfile;
import com.gmr.boot.domain.UserProfile;
import com.gmr.boot.entities.RoleEntity;
import com.gmr.boot.entities.UserEntity;
import com.gmr.boot.repositories.RoleRepository;
import com.gmr.boot.repositories.UserRepository;
import com.gmr.boot.services.LogicException;
import com.gmr.boot.services.ProfileService;
import com.gmr.boot.services.ServiceConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.nio.CharBuffer;
import java.util.List;


@Service("profileService")
@Transactional
public class ProfileServiceImpl implements ProfileService {

    private static final Logger LOG = LoggerFactory.getLogger(ProfileServiceImpl.class);

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private PasswordEncoder passwordEncoder;



    @Override
    public UserProfile getOwnProfile() throws LogicException {
        Authentication currentUser = SecurityContextHolder.getContext().getAuthentication();

        if (currentUser == null) {
            throw new LogicException(HttpStatus.UNAUTHORIZED, "No authentication information found. Are you logged in?");
        }

        UserEntity userEntity = userRepository.findByUsername(currentUser.getName());
        if (userEntity == null) {
            // This would indicate bad coding somewhere
            throw new LogicException(HttpStatus.INTERNAL_SERVER_ERROR, "Authenticated user " + currentUser.getName() +
                    " not found in database");
        }

        return userEntity.toUserProfile();
    }


    @Override
    public void register(CredentialsUserProfile profile) throws LogicException {

        if (profile.getUsername() == null || profile.getUsername().isEmpty()) {
            throw new LogicException(HttpStatus.BAD_REQUEST, "Missing username");
        }

        if (profile.getEmail() == null || profile.getEmail().isEmpty()) {
            throw new LogicException(HttpStatus.BAD_REQUEST, "Missing email");
        }

        if (!profile.getEmail().matches(".+@.+\\..+")) {
            throw new LogicException(HttpStatus.BAD_REQUEST, "Invalid email format: " + profile.getEmail());
        }

        UserEntity userEntity = userRepository.findByUsername(profile.getUsername());
        if (userEntity != null) {
            throw new LogicException(HttpStatus.BAD_REQUEST, "User " + profile.getUsername() +
                    " already exists");
        }

        userEntity = userRepository.findByEmail(profile.getEmail());
        if (userEntity != null) {
            throw new LogicException(HttpStatus.BAD_REQUEST, "Email " + profile.getEmail() +
                    " already exists with another user");
        }

        UserEntity entity = UserEntity.fromUserProfile(profile);
        List<RoleEntity> roles = roleRepository.findInByRoleName(ServiceConstants.DEFAULT_USER_ROLE_NAMES);
        entity.setRoles(roles);

        if (profile.getPassword() != null) {
            entity.setPassword(passwordEncoder.encode(CharBuffer.wrap(profile.getPassword())));
        }

        userRepository.save(entity);
    }


    @Override
    public void changePassword(char[] newPassword) throws BootException {
        Authentication currentUser = SecurityContextHolder.getContext().getAuthentication();
        if (currentUser == null) {
            throw new LogicException(HttpStatus.UNAUTHORIZED, "Attempt to change password for a non authenticated user");
        }

        changePassword(currentUser, newPassword);
    }


    @Override
    public void changePassword(char[] oldPassword, char[] newPassword) throws BootException {
        Authentication currentUser = SecurityContextHolder.getContext().getAuthentication();
        if (currentUser == null) {
            throw new LogicException(HttpStatus.UNAUTHORIZED, "Attempt to change password for a non authenticated user");
        }

        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(currentUser.getName(),
                    new String(oldPassword)));
        } catch(AuthenticationException exception) {
            throw new LogicException(HttpStatus.UNAUTHORIZED, "Wrong password", exception);
        }

        changePassword(currentUser, newPassword);
    }


    private void changePassword(Authentication currentUser, char[] newPassword) throws BootException {
        UserEntity userEntity = userRepository.findByUsername(currentUser.getName());
        if (userEntity == null) {
            // This would indicate bad coding somewhere
            throw new LogicException(HttpStatus.INTERNAL_SERVER_ERROR, "Authenticated user " + currentUser.getName() +
                    " not found in database");
        }

        LOG.info("Changing password of user " + currentUser.getName());

        userEntity.setPassword(passwordEncoder.encode(CharBuffer.wrap(newPassword)));
        userRepository.save(userEntity);
    }

}
