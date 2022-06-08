package com.saga.uni.repository;

import com.saga.uni.entity.Balance;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface BalanceRepository extends JpaRepository<Balance, UUID> {
    Balance getBalanceByAccountNo(String accountNo);
}
