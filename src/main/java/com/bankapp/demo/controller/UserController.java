package com.bankapp.demo.controller;

import com.bankapp.demo.model.User;
import com.bankapp.demo.service.UserService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {
    private UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @RequestMapping(method = RequestMethod.POST, path = "/save")
    public User adauga(@RequestBody User user) {
        return userService.save(user);
    }

    @GetMapping(path = "/get")
    public List<User> getAll() {
        return userService.getAll();
    }

    @GetMapping(path = "/users")
    public List<User> getAllUses() {
        return userService.getAllUsers();
    }

    @GetMapping(path = "/suppliers")
    public List<User> getAllSuppliers() {
        return userService.getAllSuppliers();
    }

    @DeleteMapping(path = "delete/{userId}")
    public void delete(@PathVariable Long userId) {
        userService.delete(userId);
    }
}
