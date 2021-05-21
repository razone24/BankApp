// Copyright (c) 2021 Razvan Balasa
package com.bankapp.demo.service;

import com.bankapp.demo.dto.TransactionDto;
import com.bankapp.demo.model.Account;
import com.bankapp.demo.model.Transaction;
import com.bankapp.demo.repository.TransactionRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TransactionService {

    private TransactionRepository transactionRepository;
    private DepositService depositService;

    public TransactionService(TransactionRepository transactionRepository, DepositService depositService) {
        this.transactionRepository = transactionRepository;
        this.depositService = depositService;
    }

    public Long save(Transaction transaction) {
        Transaction t = transactionRepository.save(transaction);
        if (t.getCreditor().getDeposit() != null) {
            depositService.updateAmount(t.getCreditor().getDeposit().getId(),
                    t.getCreditor().getDeposit().getAmount() + t.getAmountCreditor());
        }
        if (t.getDebtor().getDeposit() != null) {
            depositService.updateAmount(t.getDebtor().getDeposit().getId(),
                    t.getDebtor().getDeposit().getAmount() + t.getAmountDebtor());
        }
        return t.getId();
    }

    public List<Transaction> findAll() {
        return transactionRepository.findAll();
    }

    public List<TransactionDto> findAllByAccounts(List<Account> accounts) {
        List<Transaction> transactions = transactionRepository.getTransactionByCreditorOrDebtor(accounts
                .stream()
                .map(Account::getId)
                .collect(Collectors
                        .toList()));
        List<TransactionDto> transactionDtos = new ArrayList<>();
        for(Transaction transaction : transactions) {
            transactionDtos.add(TransactionDto.builder()
            .id(transaction.getId())
            .creditor(transaction.getCreditor().getAccountNo())
            .debtor(transaction.getDebtor() == null ? null : transaction.getDebtor().getAccountNo())
            .amount(transaction.getAmountCreditor())
            .details(transaction.getDetails())
            .invoiceNo(transaction.getInvoiceNo())
            .build());
        }
        return transactionDtos;
    }

    public void deleteById(Long id) {
        transactionRepository.deleteById(id);
    }
}
