// Copyright (c) 2021 Razvan Balasa
package com.bankapp.demo.repository;

import com.bankapp.demo.model.UserCredentials;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserCredentialsRepository extends JpaRepository<UserCredentials, Long> {

    Optional<UserCredentials> findUserCredentialsByUsername(String username);
}
