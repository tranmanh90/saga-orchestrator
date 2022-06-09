package com.saga.uni.service;

import com.saga.uni.entity.Balance;
import com.saga.uni.exception.InsufficientBalanceException;
import com.saga.uni.model.PaymentRequest;

public interface ITransactionService {
    Balance processTransaction(String accountNo, PaymentRequest transaction) throws InsufficientBalanceException;
}
