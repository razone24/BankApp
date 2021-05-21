// Copyright (c) 2021 Razvan Balasa
package com.bankapp.demo.utils;

import com.bankapp.demo.repository.UserCredentialsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

@Configuration
public class JpaUserDetailsService implements UserDetailsService {

    @Autowired
    private UserCredentialsRepository userCredentialsRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userCredentialsRepository.findUserCredentialsByUsername(username)
                .map(SecurityUser::new)
                .orElseThrow(() -> new BadCredentialsException("Invalid username or password!"));
    }
}
