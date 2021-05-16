package com.bankapp.demo.service;

import com.bankapp.demo.dto.TransactionDto;
import com.bankapp.demo.model.Account;
import com.bankapp.demo.model.Transaction;
import com.bankapp.demo.repository.DepositRepository;
import com.bankapp.demo.repository.TransactionRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TransactionService {

    private TransactionRepository transactionRepository;
    private DepositRepository depositRepository;

    public TransactionService(TransactionRepository transactionRepository, DepositRepository depositRepository) {
        this.transactionRepository = transactionRepository;
        this.depositRepository = depositRepository;
    }

    public Long save(Transaction transaction) {
        Transaction t = transactionRepository.save(transaction);
        if (t.getCreditor().getDeposit() != null) {
            updateDepositAmount(t.getCreditor());
        }
        if (t.getDebtor().getDeposit() != null) {
            updateDepositAmount(t.getDebtor());
        }
        return t.getId();
    }

    private void updateDepositAmount(Account account) {
        Double inwardVolume = account.getCreditedTransactions().stream()
                .mapToDouble(Transaction::getAmountCreditor)
                .sum();
        Double outwardVolume =account.getDebitedTransactions().stream()
                .mapToDouble(Transaction::getAmountDebtor)
                .sum();
        depositRepository.updateAmountById(account.getDeposit().getId(), inwardVolume - outwardVolume);
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
