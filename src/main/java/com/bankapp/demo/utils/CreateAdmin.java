// Copyright (c) 2021 Razvan Balasa
package com.bankapp.demo.utils;

import com.bankapp.demo.enums.AccountType;
import com.bankapp.demo.model.Account;
import com.bankapp.demo.model.User;
import com.bankapp.demo.model.UserCredentials;
import com.bankapp.demo.service.AccountService;
import com.bankapp.demo.service.UserCredentialsService;
import com.bankapp.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.stream.Collectors;

@Component
public class CreateAdmin {

    @Autowired
    private UserService userService;

    @Autowired
    private UserCredentialsService userCredentialsService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private AccountService accountService;

    @PostConstruct
    public void init() {
        User user = new User();
        user.setName("admin");
        user.setSupplier(false);
        user.setAddress("admin address");
        user.setEmail("admin email");
        user.setPhone("admin phone");

        if (!userService.getAllUsers().stream().map(User::getPhone).collect(Collectors.toList()).contains(user.getPhone())) {
            User addedUser = userService.save(user);

            Account account = new Account();
            account.setAccountNo("ADMIN" + System.currentTimeMillis());
            account.setType(AccountType.CURENT.name());
            account.setUser(addedUser);
            account.setCurrency("RON");
            accountService.save(account);

            UserCredentials userCredentials = new UserCredentials();
            userCredentials.setUsername("admin");
            userCredentials.setPassword(passwordEncoder.encode("1234"));
            userCredentials.setAuthority("admin");
            userCredentials.setActive(true);
            userCredentials.setUser(addedUser);
            userCredentialsService.save(userCredentials);
        }
    }
}
