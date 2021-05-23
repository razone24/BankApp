// Copyright (c) 2021 Razvan Balasa
package com.bankapp.demo.controller;

import com.bankapp.demo.model.User;
import com.bankapp.demo.model.UserCredentials;
import com.bankapp.demo.service.UserCredentialsService;
import com.bankapp.demo.service.UserService;
import com.bankapp.demo.utils.AuthorityManager;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;

@RestController
@CrossOrigin("*")
@RequestMapping("/user")
public class UserController {

    private UserService userService;

    private UserCredentialsService userCredentialsService;

    private AuthorityManager authorityManager;

    public UserController(UserService userService, UserCredentialsService userCredentialsService, AuthorityManager authorityManager) {
        this.userService = userService;
        this.userCredentialsService = userCredentialsService;
        this.authorityManager = authorityManager;
    }

    @RequestMapping(method = RequestMethod.POST, path = "/save")
    public ResponseEntity<User> save(@RequestBody User user) {
        if ("admin".equals(authorityManager.getAuthority())) {
            return ResponseEntity.ok(userService.save(user));
        } else {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(null);
        }
    }

    @GetMapping("/delete/account/{id}")
    public void deleteUserAccount(@PathVariable Long id) {
        if ("admin".equals(authorityManager.getAuthority())) {
            userCredentialsService.deactivate(id);
        }
    }

    @GetMapping(path = "/get")
    public ResponseEntity<List<User>> getAll() {
        if ("admin".equals(authorityManager.getAuthority())) {
            return ResponseEntity.ok(userService.getAll());
        } else {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(Collections.emptyList());
        }
    }

    @GetMapping(path = "/users")
    public List<User> getAllUses() {
        return userService.getAllUsers();
    }

    @GetMapping(path = "/suppliers")
    public List<User> getAllSuppliers() {
        return userService.getAllSuppliers();
    }

    @RequestMapping(method = RequestMethod.POST, path = "/credentials")
    public void saveCredentials(@RequestBody UserCredentials userCredentials) {
        if ("admin".equals(authorityManager.getAuthority())) {
            userCredentialsService.save(userCredentials);
        }
    }

    @GetMapping(path = "get/{username}")
    public ResponseEntity<User> getUserByUsername(@PathVariable String username) {
        if (authorityManager.getUsername().equals(username)) {
            return ResponseEntity.ok(userCredentialsService.getUserByUsername(username));
        } else {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(null);
        }
    }

    @DeleteMapping(path = "delete/{userId}")
    public void delete(@PathVariable Long userId) {
        if ("admin".equals(authorityManager.getAuthority())) {
            userService.delete(userId);
        }
    }
}
