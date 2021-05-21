// Copyright (c) 2021 Razvan Balasa
package com.bankapp.demo.service;

import com.bankapp.demo.model.User;
import com.bankapp.demo.repository.AccountRepository;
import com.bankapp.demo.repository.DepositRepository;
import com.bankapp.demo.repository.TransactionRepository;
import com.bankapp.demo.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    private UserRepository userRepository;
    private AccountRepository accountRepository;
    private DepositRepository depositRepository;
    private TransactionRepository transactionRepository;

    public UserService(UserRepository userRepository, AccountRepository accountRepository, DepositRepository depositRepository, TransactionRepository transactionRepository) {
        this.userRepository = userRepository;
        this.accountRepository = accountRepository;
        this.depositRepository = depositRepository;
        this.transactionRepository = transactionRepository;
    }

    public User save(User user) {
        return userRepository.save(user);
    }

    public List<User> getAll() {
        return userRepository.findAll();
    }

    public List<User> getAllUsers() {
        return userRepository.findAllBySupplier(false);
    }

    public List<User> getAllSuppliers() {
        return userRepository.findAllBySupplier(true);
    }

    public void delete(Long userId) {
        User user = userRepository.getOne(userId);
        user.getAccounts()
                .forEach(account -> {
                    account.getCreditedTransactions().forEach(transaction -> transactionRepository.delete(transaction));
                    account.getDebitedTransactions().forEach(transaction -> transactionRepository.delete(transaction));
                    if(account.getDeposit() != null){
                        depositRepository.delete(account.getDeposit());
                    }
                    accountRepository.delete(account);
                    account.getCreditedTransactions().forEach(transaction -> transactionRepository.delete(transaction));
                    account.getDebitedTransactions().forEach(transaction -> transactionRepository.delete(transaction));
                });
        userRepository.delete(user);
    }

    public User getUserByPhone(String phone) {
        return userRepository.findUserByPhone(phone);
    }
}
