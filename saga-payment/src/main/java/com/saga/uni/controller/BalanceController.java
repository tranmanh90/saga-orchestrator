package com.saga.uni.controller;

import com.saga.uni.entity.Balance;
import com.saga.uni.exception.InsufficientBalanceException;
import com.saga.uni.repository.BalanceRepository;
import com.saga.uni.service.ITransactionService;
import com.saga.uni.vo.Transaction;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import javax.persistence.NoResultException;

@AllArgsConstructor
@RestController
public class BalanceController implements IBalanceController {

    private final ITransactionService iTransactionService;
    private final BalanceRepository balanceRepository;

    @Override
    public ResponseEntity<Balance> reserveRoom(String accNo) {
        try {
            Balance balance = balanceRepository.getBalanceByAccountNo(accNo);
            return ResponseEntity.ok(balance);
        } catch (NoResultException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(null);
        }
    }

    @Override
    public ResponseEntity<Balance> transaction(String accNo, Transaction transaction) {
        try {
            Balance balance = iTransactionService.processTransaction(accNo, transaction);
            return ResponseEntity.ok(balance);
        } catch (NoResultException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(null);
        } catch (InsufficientBalanceException e) {
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE)
                    .body(null);
        }
    }
}
