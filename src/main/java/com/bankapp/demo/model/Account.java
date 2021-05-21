// Copyright (c) 2021 Razvan Balasa
package com.bankapp.demo.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String accountNo;

    private String type;
    private String currency;

    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(mappedBy = "debtor", fetch = FetchType.LAZY)
    @JsonIgnore
    private List<Transaction> debitedTransactions;

    @OneToMany(mappedBy = "creditor", fetch = FetchType.LAZY)
    @JsonIgnore
    private List<Transaction> creditedTransactions;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "deposit_id", referencedColumnName = "id")
    private Deposit deposit;
}
