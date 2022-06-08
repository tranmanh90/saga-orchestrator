package com.saga.uni.service;

import com.saga.uni.entity.Balance;
import com.saga.uni.exception.InsufficientBalanceException;
import com.saga.uni.vo.Transaction;

public interface ITransactionService {
    Balance processTransaction(String accountNo, Transaction transaction) throws InsufficientBalanceException;
}
