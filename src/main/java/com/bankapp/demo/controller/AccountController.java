// Copyright (c) 2021 Razvan Balasa
package com.bankapp.demo.controller;

import com.bankapp.demo.dto.AccountDto;
import com.bankapp.demo.dto.DepositDto;
import com.bankapp.demo.model.Account;
import com.bankapp.demo.service.AccountService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin("*")
@RequestMapping("/account")
public class AccountController {

    private AccountService accountService;

    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @RequestMapping(method = RequestMethod.POST, path = "/save")
    public Account save(@RequestBody Account account) {
        return accountService.save(account);
    }

    @GetMapping(path = "/get")
    public List<Account> getAll() {
        return accountService.getAll();
    }

    @GetMapping(path = "/get/{userId}")
    public List<AccountDto> getAccountsWithFundsForUser(@PathVariable Long userId) {
        return accountService.getAllAccountsWithFundsForUser(userId);
    }

    @GetMapping(path = "/get/deposit/{userId}")
    public List<DepositDto> getDepositsForUser(@PathVariable Long userId) {
        return accountService.getAllDepositsForUser(userId);
    }
}
