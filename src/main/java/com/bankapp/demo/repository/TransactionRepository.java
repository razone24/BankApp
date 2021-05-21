// Copyright (c) 2021 Razvan Balasa
package com.bankapp.demo.repository;

import com.bankapp.demo.model.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {

    @Query("select t from Transaction t where t.creditor.id in :account or t.debtor.id in :account")
    public List<Transaction> getTransactionByCreditorOrDebtor(@Param("account") List<Long> accountsNo);
}
