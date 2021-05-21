// Copyright (c) 2021 Razvan Balasa
package com.bankapp.demo.utils;

import lombok.NoArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class AuthenticationManager {



    public String getAuthority() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return  authentication.getAuthorities()
                .stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList())
                .get(0);

    }
}
