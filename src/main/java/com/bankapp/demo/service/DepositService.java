// Copyright (c) 2021 Razvan Balasa
package com.bankapp.demo.service;

import com.bankapp.demo.repository.DepositRepository;
import org.springframework.stereotype.Service;

@Service
public class DepositService {

    private DepositRepository depositRepository;

    public DepositService(DepositRepository depositRepository) {
        this.depositRepository = depositRepository;
    }

    public int updateRemainedTime(Long time, Long id) {
        return depositRepository.updateRemainedTime(id, time);
    }

    public int updateAmount(Long id, Double amount) {
        return depositRepository.updateAmountById(id, amount);
    }
}
