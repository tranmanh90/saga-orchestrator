package com.saga.uni.controller;

import com.saga.uni.dto.request.CreateAccountRequest;
import com.saga.uni.dto.response.CreateAccountResponse;
import com.saga.uni.entity.Balance;
import com.saga.uni.model.PaymentRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/api/payment")
public interface IBalanceController {

    @GetMapping("/getBalance/{accNo}")
    ResponseEntity<Balance> reserveRoom(@PathVariable String accNo);

    @PostMapping("/transaction/{accNo}")
    ResponseEntity<Balance> transaction(@PathVariable String accNo, @RequestBody PaymentRequest transaction);

    @PostMapping("/createAccount")
    ResponseEntity<CreateAccountResponse> createAccount(@RequestBody CreateAccountRequest request);
}
