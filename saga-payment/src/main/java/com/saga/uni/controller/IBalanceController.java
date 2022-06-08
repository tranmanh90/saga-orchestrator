package com.saga.uni.controller;

import com.saga.uni.entity.Balance;
import com.saga.uni.vo.Transaction;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/api/payment")
public interface IBalanceController {

    @GetMapping("/getBalance/{accNo}")
    ResponseEntity<Balance> reserveRoom(@PathVariable String accNo);

    @PostMapping("/transaction/{accNo}")
    ResponseEntity<Balance> transaction(@PathVariable String accNo, @RequestBody Transaction transaction);
}
