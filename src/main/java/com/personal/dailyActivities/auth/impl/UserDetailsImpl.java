package com.personal.dailyActivities.auth.impl;

import com.personal.dailyActivities.auth.entity.UserRegister;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

public class UserDetailsImpl implements UserDetails {
    private final UserRegister userRegister;

    public UserDetailsImpl(UserRegister userRegister) {
        this.userRegister = userRegister;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of();
    }

    @Override
    public String getPassword() {
        return userRegister.getPassWord();
    }

    @Override
    public String getUsername() {
        return userRegister.getUserName();
    }
}
