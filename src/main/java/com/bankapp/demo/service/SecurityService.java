// Copyright (c) 2021 Razvan Balasa
package com.bankapp.demo.service;

import com.bankapp.demo.dto.AuthenticationRequestDto;
import com.bankapp.demo.dto.AuthenticationResponseDto;
import com.bankapp.demo.utils.CustomAuthenticationProvider;
import com.bankapp.demo.utils.JpaUserDetailsService;
import com.bankapp.demo.utils.JwtUtil;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
public class SecurityService {

    private CustomAuthenticationProvider authenticationProvider;

    private JwtUtil jwtUtil;

    private JpaUserDetailsService userDetailsService;

    public SecurityService(CustomAuthenticationProvider authenticationProvider, JwtUtil jwtUtil, JpaUserDetailsService userDetailsService) {
        this.authenticationProvider = authenticationProvider;
        this.jwtUtil = jwtUtil;
        this.userDetailsService = userDetailsService;
    }

    public ResponseEntity<?> createAuthenticationToken(AuthenticationRequestDto authenticationRequest) {
        try {
            authenticationProvider.authenticate(
                    new UsernamePasswordAuthenticationToken(authenticationRequest.getUsername(), authenticationRequest.getPassword())
            );
        }
        catch (BadCredentialsException e) {
            throw new RuntimeException("Incorrect username or password", e);
        }

        final UserDetails userDetails = userDetailsService
                .loadUserByUsername(authenticationRequest.getUsername());

        final String jwt = jwtUtil.generateToken(userDetails);

        return ResponseEntity.ok(new AuthenticationResponseDto(jwt));
    }
}
