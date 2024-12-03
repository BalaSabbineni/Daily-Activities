package com.personal.dailyActivities.auth.impl;

import com.personal.dailyActivities.auth.repository.UserRegisterRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class UserDetailsServiceImpl implements UserDetailsService {

    public UserDetailsServiceImpl(UserRegisterRepository userRegisterRepository) {
        this.userRegisterRepository = userRegisterRepository;
    }

    private final UserRegisterRepository userRegisterRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        var user = userRegisterRepository.findUserByUserName(username);
        if (Objects.isNull(user)) {
            throw new UsernameNotFoundException("User name not found");
        }
        return new UserDetailsImpl(user);
    }
}
