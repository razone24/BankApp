package com.bankapp.demo.controller;

import com.bankapp.demo.dto.TransactionDto;
import com.bankapp.demo.model.Account;
import com.bankapp.demo.model.Transaction;
import com.bankapp.demo.service.TransactionService;
import com.bankapp.demo.utils.AuthorityManager;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("/transactions")
public class TransactionController {

    private TransactionService transactionService;
    private AuthorityManager authorityManager;

    public TransactionController(TransactionService transactionService, AuthorityManager authorityManager) {
        this.transactionService = transactionService;
        this.authorityManager = authorityManager;
    }

    @RequestMapping(method = RequestMethod.POST,path = "/save")
    public Long save(@RequestBody Transaction transaction) {
        return transactionService.save(transaction);
    }

    @GetMapping(path = "/get")
    public ResponseEntity<List<Transaction>> findAll() {

        if ("admin".equals(authorityManager.getAuthority())) {
            return ResponseEntity.ok(transactionService.findAll());
        } else {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(Collections.emptyList());
        }
    }

    @GetMapping(path = "/getAllByUser")
    public List<TransactionDto> findAllByAccounts(@RequestBody List<Account> accounts) {
        return transactionService.findAllByAccounts(accounts);
    }

    @RequestMapping(method = RequestMethod.DELETE, path = "/delete/{transactionId}")
    public void deleteById(@PathVariable Long transactionId) {
        if ("admin".equals(authorityManager.getAuthority())) {
            transactionService.deleteById(transactionId);
        }
    }
}
