package com.bankapp.demo.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Builder
@Getter
@Setter
public class TransactionDto implements Serializable {

    private transient long id;
    private transient String creditor;
    private transient String debtor;
    private transient double amount;
    private transient String details;
    private transient String invoiceNo;
}
