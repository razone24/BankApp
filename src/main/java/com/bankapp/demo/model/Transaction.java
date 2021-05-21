// Copyright (c) 2021 Razvan Balasa
package com.bankapp.demo.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "creditor_id")
    private Account creditor;

    @ManyToOne
    @JoinColumn(name = "debtor_id")
    private Account debtor;

    private double amountCreditor;
    private double amountDebtor;
    private String details;
    private String invoiceNo;


}
