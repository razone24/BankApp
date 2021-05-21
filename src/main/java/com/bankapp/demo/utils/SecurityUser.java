// Copyright (c) 2021 Razvan Balasa
package com.bankapp.demo.utils;

import com.bankapp.demo.model.UserCredentials;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;

public class SecurityUser implements UserDetails {

    private final UserCredentials userCredentials;

    public SecurityUser(UserCredentials userCredentials) {
        this.userCredentials = userCredentials;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singleton(userCredentials::getAuthority);
    }

    @Override
    public String getPassword() {
        return userCredentials.getPassword();
    }

    @Override
    public String getUsername() {
        return userCredentials.getUsername();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return userCredentials.isActive();
    }
}
