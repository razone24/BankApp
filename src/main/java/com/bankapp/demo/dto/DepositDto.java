// Copyright (c) 2021 Razvan Balasa
package com.bankapp.demo.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Builder
@Getter
@Setter
public class DepositDto implements Serializable {

    private transient long id;
    private transient String accountNo;
    private transient String type;
    private transient String currency;
    private transient double amount;
    private transient long time;
    private transient long remainedTime;
    private transient double interest;
}
