// Copyright (c) 2021 Razvan Balasa
package com.bankapp.demo.service;

import com.bankapp.demo.model.User;
import com.bankapp.demo.model.UserCredentials;
import com.bankapp.demo.repository.UserCredentialsRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserCredentialsService {

    private UserCredentialsRepository userCredentialsRepository;
    private PasswordEncoder passwordEncoder;

    public UserCredentialsService(UserCredentialsRepository userCredentialsRepository, PasswordEncoder passwordEncoder) {
        this.userCredentialsRepository = userCredentialsRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public void save(UserCredentials userCredentials) {
        userCredentials.setPassword(passwordEncoder.encode(userCredentials.getPassword()));
        userCredentialsRepository.save(userCredentials);
    }

    public void deactivate(Long userId) {
        userCredentialsRepository.updateUserState(userId);
    }

    public User getUserByUsername(String username) {
        return userCredentialsRepository.findUserCredentialsByUsername(username).get().getUser();
    }
}
