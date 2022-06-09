package com.saga.uni.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PaymentRequest {
    private String accountNo;
    private UUID trxIdentifier;
    private TransactionType trxType;
    private Double value;

    public enum TransactionType {
        WITHDRAW,
        DEPOSIT
    }
}