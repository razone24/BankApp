package com.bankapp.demo.repository;

import com.bankapp.demo.model.Deposit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;


public interface DepositRepository extends JpaRepository<Deposit, Long> {

    @Query("update Deposit d set d.amount = :amount where  d.id = :id")
    int updateAmountById(@Param("id") long id, @Param("amount") double amount);
}
