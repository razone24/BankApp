// Copyright (c) 2021 Razvan Balasa
package com.bankapp.demo.service;

import com.bankapp.demo.dto.AccountDto;
import com.bankapp.demo.dto.DepositDto;
import com.bankapp.demo.enums.AccountType;
import com.bankapp.demo.model.Account;
import com.bankapp.demo.model.Transaction;
import com.bankapp.demo.repository.AccountRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class AccountService {

    private AccountRepository accountRepository;

    public AccountService(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    public Account save(Account account) {
        return accountRepository.save(account);
    }

    public List<Account> getAll() {
        return accountRepository.findAll();
    }

    public List<AccountDto> getAllAccountsWithFundsForUser(Long userId) {
        List<Account> accounts = accountRepository.findAllByUserId(userId);
        List<AccountDto> accountDtos = new ArrayList<>();
        for(Account account : accounts) {
            Double inwardVolume = account.getCreditedTransactions().stream().mapToDouble(Transaction::getAmountCreditor).sum();
            Double outwardVolume = account.getDebitedTransactions().stream().mapToDouble(Transaction::getAmountDebtor).sum();
            accountDtos.add(AccountDto
                    .builder()
                    .id(account.getId())
                    .accountNo(account.getAccountNo())
                    .currency(account.getCurrency())
                    .type(account.getType())
                    .amount((inwardVolume - outwardVolume))
                    .build());
        }
        return accountDtos;
    }

    public List<DepositDto> getAllDepositsForUser(Long userId) {
        List<Account> accounts = accountRepository.findAllByUserIdAndType(userId, AccountType.ECONOMII.name());
        List<DepositDto> depositDtos = new ArrayList<>();
        for(Account account : accounts) {
            Double inwardVolume = account.getCreditedTransactions().stream().mapToDouble(Transaction::getAmountCreditor).sum();
            Double outwardVolume = account.getDebitedTransactions().stream().mapToDouble(Transaction::getAmountDebtor).sum();
            depositDtos.add(DepositDto.builder()
            .id(account.getDeposit().getId())
            .accountNo(account.getAccountNo())
            .type(account.getType())
            .currency(account.getCurrency())
            .amount(inwardVolume - outwardVolume)
            .time(account.getDeposit().getTime())
            .interest(account.getDeposit().getInterest())
            .remainedTime(account.getDeposit().getRemainedTime())
            .build());
        }
        return depositDtos;
    }

    public List<Account> getAllAccountsByUserId(Long id) {
        return accountRepository.findAllByUserId(id);
    }
}
