package com.bankapp.demo.repository;

import com.bankapp.demo.model.Account;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AccountRepository extends JpaRepository<Account, String> {

    public List<Account> findAllByUserId(Long userId);

    public List<Account> findAllByUserIdAndType(Long userId, String accountType);
}
