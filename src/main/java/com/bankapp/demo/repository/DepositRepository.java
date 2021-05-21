// Copyright (c) 2021 Razvan Balasa
package com.bankapp.demo.repository;

import com.bankapp.demo.model.Deposit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;


public interface DepositRepository extends JpaRepository<Deposit, Long> {

    @Transactional
    @Modifying
    @Query("update Deposit d set d.amount = :amount where  d.id = :id")
    int updateAmountById(@Param("id") long id, @Param("amount") double amount);

    @Transactional
    @Modifying
    @Query("update Deposit d set d.remainedTime = :time where  d.id = :id")
    int updateRemainedTime(@Param("id") long id, @Param("time") long amount);
}
