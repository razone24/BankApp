// Copyright (c) 2021 Razvan Balasa
package com.bankapp.demo.repository;

import com.bankapp.demo.model.UserCredentials;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import javax.transaction.Transactional;
import java.util.Optional;

public interface UserCredentialsRepository extends JpaRepository<UserCredentials, Long> {

    Optional<UserCredentials> findUserCredentialsByUsername(String username);

    @Transactional
    @Modifying
    @Query("update UserCredentials uc set uc.active = false where uc.user.id = :userId")
    void updateUserState(Long userId);
}
