// Copyright (c) 2021 Razvan Balasa
package com.bankapp.demo.repository;

import com.bankapp.demo.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Long> {

    List<User> findAllBySupplier(Boolean supplier);

    User findUserByPhone(String phone);
}
