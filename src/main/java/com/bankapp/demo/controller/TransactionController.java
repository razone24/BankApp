package com.bankapp.demo.controller;

import com.bankapp.demo.dto.TransactionDto;
import com.bankapp.demo.model.Account;
import com.bankapp.demo.model.Transaction;
import com.bankapp.demo.service.TransactionService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/transactions")
public class TransactionController {

    private TransactionService transactionService;

    public TransactionController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @RequestMapping(method = RequestMethod.POST,path = "/save")
    public Long save(@RequestBody Transaction transaction) {
        return transactionService.save(transaction);
    }

    @GetMapping(path = "/get")
    public List<Transaction> findAll() {
        return transactionService.findAll();
    }

    @GetMapping(path = "/getAllByUser")
    public List<TransactionDto> findAllByAccounts(@RequestBody List<Account> accounts) {
        return transactionService.findAllByAccounts(accounts);
    }

    @RequestMapping(method = RequestMethod.DELETE, path = "/delete/{transactionId}")
    public void deleteById(@PathVariable Long transactionId) {
        transactionService.deleteById(transactionId);
    }
}
