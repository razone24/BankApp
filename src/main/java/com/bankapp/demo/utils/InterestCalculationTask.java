// Copyright (c) 2021 Razvan Balasa
package com.bankapp.demo.utils;

import com.bankapp.demo.model.Account;
import com.bankapp.demo.model.Deposit;
import com.bankapp.demo.model.Transaction;
import com.bankapp.demo.model.User;
import com.bankapp.demo.service.AccountService;
import com.bankapp.demo.service.DepositService;
import com.bankapp.demo.service.TransactionService;
import com.bankapp.demo.service.UserService;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Component
public class InterestCalculationTask {

    private AccountService accountService;
    private UserService userService;
    private TransactionService transactionService;
    private DepositService depositService;

    public InterestCalculationTask(AccountService accountService, UserService userService, TransactionService transactionService, DepositService depositService) {
        this.accountService = accountService;
        this.userService = userService;
        this.transactionService = transactionService;
        this.depositService = depositService;
    }

    @Scheduled(cron = "0 0 0 ? * *")
    public void compute() {
        List<Account> accounts = accountService.getAll();
        List<Deposit> deposits = accounts
                                .stream()
                                .map(Account::getDeposit)
                                .filter(Objects::nonNull)
                                .collect(Collectors.toList());
        User user = userService.getUserByPhone("admin phone");
        Account adminAccount = accountService.getAllAccountsByUserId(user.getId()).get(0);
        for(Deposit deposit : deposits) {
            double value = (deposit.getAmount() * deposit.getInterest()) / 100;
            Transaction transaction = new Transaction();
            transaction.setDebtor(adminAccount);
            transaction.setCreditor(deposit.getAccount());
            transaction.setAmountCreditor(value);
            transaction.setAmountDebtor(value);
            transaction.setDetails("Dobanda depozit");
            transactionService.save(transaction);
            depositService.updateRemainedTime(deposit.getRemainedTime() - 1, deposit.getId());
        }
    }
}
