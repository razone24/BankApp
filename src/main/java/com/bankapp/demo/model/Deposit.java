package com.bankapp.demo.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
public class Deposit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private double amount;
    private long time;
    private long remainedTime;
    private double interest;

    @OneToOne(mappedBy = "deposit", cascade = CascadeType.ALL)
    @JsonIgnore
    private Account account;
}
