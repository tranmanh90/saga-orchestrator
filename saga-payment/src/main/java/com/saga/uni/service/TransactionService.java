package com.saga.uni.service;

import com.saga.uni.entity.Balance;
import com.saga.uni.exception.InsufficientBalanceException;
import com.saga.uni.model.PaymentRequest;
import com.saga.uni.repository.BalanceRepository;
import org.springframework.stereotype.Service;

@Service
public class TransactionService implements ITransactionService {

    private final BalanceRepository balanceRepository;

    public TransactionService(BalanceRepository balanceRepository) {
        this.balanceRepository = balanceRepository;
    }

    @Override
    public Balance processTransaction(String accountNo, PaymentRequest transaction) throws InsufficientBalanceException {
        Balance balance = balanceRepository.getBalanceByAccountNo(accountNo);
        switch (transaction.getTrxType()) {
            case DEPOSIT:
                balance.deposit(transaction.getValue());
                break;
            case WITHDRAW:
                balance.withdraw(transaction.getValue());
                break;
        }
        balanceRepository.save(balance);
        return balance;
    }
}
